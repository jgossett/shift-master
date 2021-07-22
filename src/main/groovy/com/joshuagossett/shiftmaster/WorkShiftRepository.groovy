package com.joshuagossett.shiftmaster

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

@Repository
@Transactional(readOnly = true)
interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {

    @Query("""
        SELECT ws 
        FROM WorkShift ws
        WHERE
            (:startTime IS NULL OR :startTime <= ws.startTime)
            AND (:endTime IS NULL OR ws.endTime <= :endTime)
            AND ws.isSoftDeleted = false
        ORDER BY ws.startTime DESC
     """)
    List<WorkShift> findAllByBetweenStartTimeAndEndTime(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    )

    WorkShift findByIdAndIsSoftDeletedFalse(Long id)

    @Query("""
        SELECT 
            CASE 
                WHEN 0 < COUNT(ws) THEN true
                ELSE false
            END
        FROM WorkShift ws
        WHERE
            (
                (
                    :startTime <= ws.startTime
                    AND ws.startTime <= :endTime
                )
                OR
                (
                    :startTime <= ws.endTime
                    AND ws.endTime <= :endTime
                )
           )
           AND
           (
             :excludeId IS NULL OR ws.id != :excludeId
           )
           AND ws.isSoftDeleted = false
    """)
    boolean existsByStartTimeAndEndTime(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludeId") id
    )
}
