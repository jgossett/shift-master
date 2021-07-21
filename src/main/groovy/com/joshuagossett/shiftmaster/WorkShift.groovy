package com.joshuagossett.shiftmaster

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.time.LocalDateTime

@Entity
class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    LocalDateTime startTime
    LocalDateTime endTime
    boolean isSoftDeleted
}
