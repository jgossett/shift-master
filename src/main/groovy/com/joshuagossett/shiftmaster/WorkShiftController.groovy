package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.time.LocalDateTime

@RestController
@RequestMapping(
        path = '/WorkShift',
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
)
class WorkShiftController {

    @Autowired
    WorkShiftService workShiftService

    @PostMapping
    void save(@RequestBody @Valid WorkShift workShift) {
        workShiftService.create(workShift)
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
    Optional<WorkShift> show(@PathVariable Long id) {
        workShiftService.get(id)
    }

    @PutMapping('/{id}')
    void update(@PathVariable Long id, @RequestBody @Valid WorkShift workShift) {
        workShiftService.update(id, workShift)
    }

    @DeleteMapping('/{id}')
    void delete(@PathVariable Long id) {
        workShiftService.softDelete(id)
    }

}
