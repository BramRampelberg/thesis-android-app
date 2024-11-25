package com.example.android_2425_gent2.di.module


import com.example.android_2425_gent2.BuildConfig
import com.example.android_2425_gent2.data.network.reservation.ReservationApiService
import com.example.android_2425_gent2.data.network.timeslot.TimeSlotApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type
import java.time.LocalDate

class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_LOCAL_TIME))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        return LocalTime.parse(json?.asString, DateTimeFormatter.ISO_LOCAL_TIME)
    }
}


class LocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json?.asString, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

object NetworkModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    val gson = GsonBuilder()
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val timeSlotApiService: TimeSlotApiService by lazy {
        retrofit.create(TimeSlotApiService::class.java)
    }

    val reservationApiService: ReservationApiService by lazy {
        retrofit.create(ReservationApiService::class.java)
    }
}