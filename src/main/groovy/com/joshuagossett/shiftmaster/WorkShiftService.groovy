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
    private static final COPY_EXCLUDED_PROPERTIES = ['id', 'createdOn', 'updatedOn', 'version', 'class', 'metaClass']

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

    @Transactional
    void create(WorkShift workShift) {
        validateStartTimeAndEndTime(workShift)

        workShiftRepository.save(workShift)
    }

    @Transactional
    void update(long id, WorkShift source) {
        validateStartTimeAndEndTime(source, id)

        WorkShift destination = workShiftRepository.findByIdAndIsSoftDeletedFalse(id).get()
        copyProperties(source, destination)

        workShiftRepository.save(destination)
    }

    private void copyProperties(Object source, Object destination) {
        source.properties.each { String key, Object value ->
            // skip property
            def isBlacklistedProperty = { String propertyName -> propertyName in COPY_EXCLUDED_PROPERTIES }
            if (!destination.hasProperty(key) || isBlacklistedProperty(key)) {
                return
            }

            destination[key] = source[key]
        }
    }

    private void validateStartTimeAndEndTime(WorkShift workShift, Long excludeId = null) {
        boolean exists = workShiftRepository.existsByStartTimeAndEndTime(workShift.startTime, workShift.endTime, excludeId)
        if (!exists) {
            return
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "The Work Shift's startTime and endTime overlaps with another Work Shift."
        )
    }
}
