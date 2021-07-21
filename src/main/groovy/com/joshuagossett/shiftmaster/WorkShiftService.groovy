package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        workShiftRepository.save(workShift)
    }

    @Transactional
    void update(long id, WorkShift source) {
        WorkShift destination = workShiftRepository.getById(id)
        copyProperties(source, destination)

        workShiftRepository.save(destination)
    }

    void copyProperties(Object source, Object destination){
        source.properties.each{String key, Object value ->
            boolean isBlacklistedProperty = key in COPY_EXCLUDED_PROPERTIES
            if(!destination.hasProperty(key) || isBlacklistedProperty){
                return
            }

            destination[key] = source[key]
        }
    }
}
