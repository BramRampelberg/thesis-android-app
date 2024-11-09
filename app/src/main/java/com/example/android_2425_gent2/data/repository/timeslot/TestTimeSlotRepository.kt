package com.example.android_2425_gent2.data.repository.timeslot

import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.data.remote.model.TimeSlotResponse

class TestTimeSlotRepository : TimeSlotRepository {
    override suspend fun getTimeSlotsForRange(start:String, end:String): TimeSlotResponse{
        return TimeSlotResponse(
            start = "2024-10-01",
            end = "2024-10-31",
            days = listOf(
                DayInfo(date = "2024-10-01", isSlotAvailable = true, isFullyBooked = false),
                DayInfo(date = "2024-10-02", isSlotAvailable = false, isFullyBooked = true)

        ),
            totalDays = 30
        )


    }
    override suspend fun getTimeSlotsForDay(year: Int, month: Int, day: Int): List<TimeSlot>{
        return listOf(
            TimeSlot(id = 1, start = "09:00", end = "11:30", isBookedByUser = false),
        )
    }
}

