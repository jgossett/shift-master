package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.time.LocalDateTime

@RestController
@RequestMapping(
        path = 'work-shifts',
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
)
class WorkShiftController {

    @Autowired
    WorkShiftService workShiftService

    @PostMapping
    void save(@RequestBody @Valid WorkShiftRequest workShiftRequest) {
        WorkShift workShift = toWorkShift(null, workShiftRequest)
        workShiftService.createOrUpdate(workShift)
    }

    @GetMapping
    List<WorkShift> index(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime endTime
    ) {
        workShiftService.findAllByBetweenStartTimeAndEndTime(startTime, endTime)
    }

    @GetMapping('/{id}')
    WorkShift show(@PathVariable Long id) {
        workShiftService.get(id)
    }

    @PutMapping('/{id}')
    void update(@PathVariable Long id, @RequestBody @Valid WorkShiftRequest workShiftRequest) {
        WorkShift workShift = toWorkShift(id, workShiftRequest)
        workShiftService.createOrUpdate(workShift)
    }

    @DeleteMapping('/{id}')
    void delete(@PathVariable Long id) {
        workShiftService.softDelete(id)
    }

    private WorkShift toWorkShift(Long id, WorkShiftRequest workShiftRequest) {
        WorkShift workShift = id ? workShiftService.get(id) : new WorkShift()

        workShift.startTime = workShiftRequest.startTime
        workShift.endTime = workShiftRequest.endTime

        workShift
    }
}
