package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class WorkShiftService {

    @Autowired
    WorkShiftRepository workShiftRepository

    List<WorkShift> findAllByBetweenStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        workShiftRepository.findAllByBetweenStartTimeAndEndTime(startTime, endTime)
    }

    Optional<WorkShift> get(Long id) {
        workShiftRepository.findByIdAndIsSoftDeletedFalse(id)
    }

    @Transactional
    void softDelete(Long id) {
        WorkShift workShift = workShiftRepository.findByIdAndIsSoftDeletedFalse(id).get()
        workShift.isSoftDeleted = true
        workShiftRepository.save(workShift)
    }

}
