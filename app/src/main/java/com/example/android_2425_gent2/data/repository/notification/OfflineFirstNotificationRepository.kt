package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.local.dao.OfflineNotificationDao
import com.example.android_2425_gent2.data.local.entity.asExternalModel
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
    override suspend fun getNotifications(): Flow<APIResource<NotificationResponse>> = flow {
        //emit loading
        emit(APIResource.Loading())

        val notificationsFlow = notificationDao.getOfflineNotifications()
            .distinctUntilChanged()
            .map {
            localNotifications ->
                APIResource.Success(
                    NotificationResponse(
                        notifications = localNotifications.map { it.asExternalModel() }
                    )
                )
            }

        //launch network request load in data in local db
        try {
            val response = remoteApiService.getNotifications()

            //update local database
            withContext(Dispatchers.IO) {
                notificationDao.insert(response.notifications.map {it.asEntity() })
            }

            delay(100)
        } catch (e: Exception) {
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