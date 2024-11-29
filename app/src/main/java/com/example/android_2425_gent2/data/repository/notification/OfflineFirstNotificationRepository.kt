package com.example.android_2425_gent2.data.repository.notification

import android.util.Log
import com.example.android_2425_gent2.data.local.dao.OfflineNotificationDao
import com.example.android_2425_gent2.data.local.entity.asExternalModel
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.network.model.NotificationResponse
import com.example.android_2425_gent2.data.network.model.asEntity
import com.example.android_2425_gent2.data.network.notification.NotificationApiService
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OfflineFirstNotificationRepository(
    private val notificationDao: OfflineNotificationDao,
    private val remoteApiService: NotificationApiService
): NotificationRepository {

    /**
     * get notifications from local db
     * fetch from network and update local db
     */
    override suspend fun getNotifications(): Flow<APIResource<List<NotificationDto>>> = flow {
        //emit loading
        emit(APIResource.Loading())

        val notificationsFlow = notificationDao.getOfflineNotifications()
            .distinctUntilChanged()
            .map {
            localNotifications ->
                APIResource.Success(
                    localNotifications.map { it.asExternalModel() }
                )
            }

        //launch network request load in data in local db
        try {
            println("Trying to fetch data")
            val response = remoteApiService.getNotifications()

            print("1")
            //update local database
            withContext(Dispatchers.IO) {
                notificationDao.insert(response.map {it.asEntity() })
            }

            print("2")
            delay(100)
        } catch (e: Exception) {
            println("Inside exception")
            e.printStackTrace()
            Log.e("Inside exception", e.message?: "unknown message")
            val localData = notificationDao.getOfflineNotifications().first()
            if(localData.isEmpty()) {
                emit(APIResource.Error("No notifications found"))
                return@flow
            }
        }

        //collect and emit
        notificationsFlow.collect {
                emission ->
            emit(emission)
        }
    }.flowOn(Dispatchers.IO)

}