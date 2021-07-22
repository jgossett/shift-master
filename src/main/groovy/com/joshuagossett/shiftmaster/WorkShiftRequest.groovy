package com.joshuagossett.shiftmaster

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull
import java.time.LocalDateTime

class WorkShiftRequest {

    @NotNull
    LocalDateTime startTime

    @NotNull
    LocalDateTime endTime

    @AssertTrue(message = "The 'startTime' must not before the 'endTime'.")
    boolean isStartTimeBeforeEndTime() {
        startTime < endTime
    }

}
