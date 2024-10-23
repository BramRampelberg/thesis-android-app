package com.example.android_2425_gent2.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import com.example.android_2425_gent2.data.local.entity.UserEntity
import com.example.android_2425_gent2.data.local.entity.UserReservationCrossRef
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RemoteReservationDaoTest {
    private lateinit var reservationDao: ReservationDao
    private lateinit var userDao: UserDao
    private lateinit var userReservationDao: UserReservationDao
    
    private lateinit var appDatabase: AppDatabase
    private val reservation1 = ReservationEntity(
        reservationId = 1,
        boatId = 1,
        timeSlotId = 1
    )
    private val reservation2 = ReservationEntity(
        reservationId = 2,
        boatId = 2,
        timeSlotId = 2
    )
    private val user1 = UserEntity(userId = 1)
    
    @Before
    fun setup() {
        createDb()
        runBlocking {
            insertUser()
        }
    }
    
    private fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        reservationDao = appDatabase.reservationDao()
        userDao = appDatabase.userDao()
        userReservationDao = appDatabase.userReservationDao()
    }
    
    private suspend fun insertUser() {
        userDao.insert(user1)
    }
    
    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
    
    private suspend fun addOneReservationToDb() {
        reservationDao.insert(reservation1)
    }
    
    private suspend fun addTwoReservationsToDb() {
        reservationDao.insert(reservation1)
        reservationDao.insert(reservation2)
    }
    
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsReservationIntoDB() = runBlocking {
        addOneReservationToDb()
        val reservation: ReservationEntity? = reservationDao.getReservationById(1).first()
        assertEquals(reservation, reservation1)
    }
    
    @Test
    @Throws(Exception::class)
    fun daoUpdate_updatesReservationInDB() = runBlocking {
        val updatedReservation = ReservationEntity(reservationId = 1, boatId = 3, timeSlotId = 3)
        addOneReservationToDb()
        reservationDao.update(updatedReservation)
        val reservation: ReservationEntity? = reservationDao.getReservationById(1).first()
        assertEquals(reservation, updatedReservation)
    }
    
    @Test
    @Throws(Exception::class)
    fun daoDelete_deletesReservationInDB() = runBlocking {
        addOneReservationToDb()
        reservationDao.delete(reservation1)
        val reservation: ReservationEntity? = reservationDao.getReservationById(1).first()
        assertEquals(reservation, null)
    }
    
    @Test
    @Throws(Exception::class)
    fun daoGetReservationsByUser_returnsAllReservationsOfUserInDB() = runBlocking {
        addTwoReservationsToDb()
        val userReservationCrossRef1: UserReservationCrossRef =
            UserReservationCrossRef(user1.userId, reservation1.reservationId)
        val userReservationCrossRef2: UserReservationCrossRef =
            UserReservationCrossRef(user1.userId, reservation2.reservationId)
        userReservationDao.insert(userReservationCrossRef1)
        userReservationDao.insert(userReservationCrossRef2)
        
        val reservations = reservationDao.getReservationsByUser(user1.userId).first()
        assertEquals(reservations[0], reservation1)
        assertEquals(reservations[1], reservation2)
    }
    
}