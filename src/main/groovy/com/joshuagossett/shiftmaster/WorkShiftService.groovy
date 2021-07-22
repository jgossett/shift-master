package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class WorkShiftService {
    @Autowired
    WorkShiftRepository workShiftRepository

    List<WorkShift> findAllByBetweenStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        workShiftRepository.findAllByBetweenStartTimeAndEndTime(startTime, endTime)
    }

    WorkShift get(Long id) {
        workShiftRepository.findByIdAndIsSoftDeletedFalse(id)
    }

    @Transactional
    void softDelete(Long id) {
        WorkShift workShift = workShiftRepository.findByIdAndIsSoftDeletedFalse(id).get()
        workShift.isSoftDeleted = true
        workShiftRepository.save(workShift)
    }

    @Transactional
    void createOrUpdate(WorkShift workShift) {
        validateStartTimeAndEndTime(workShift)
        workShiftRepository.save(workShift)
    }

    private void validateStartTimeAndEndTime(WorkShift workShift) {
        boolean exists = workShiftRepository.existsByStartTimeAndEndTime(
                workShift.startTime,
                workShift.endTime,
                workShift.id
        )

        if (!exists) {
            return
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "The Work Shift's startTime and endTime overlaps with another Work Shift."
        )
    }
}
