package com.joshuagossett.shiftmaster

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

@Repository
@Transactional(readOnly = true)
interface WorkShiftService extends JpaRepository<WorkShift, Long> {

    @Query("""
        SELECT ws 
        FROM WorkShift ws
        WHERE (:startTime IS NULL or :startTime <= ws.startTime)
            AND (:endTime IS NULL or ws.endTime <= :endTime)
        ORDER BY ws.startTime DESC
     """)
    List<WorkShift> findAllByBetweenStartTimeAndEndTime(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    )

}
