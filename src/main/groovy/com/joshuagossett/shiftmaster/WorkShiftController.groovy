package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.Example
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime

@RestController
@RequestMapping(path = "/WorkShift", produces = [MediaType.APPLICATION_JSON_VALUE])
class WorkShiftController {
    @Autowired
    WorkShiftService workShiftService

    @GetMapping
    def index(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime endTime
    ) {
        workShiftService.findAllByBetweenStartTimeAndEndTime(startTime, endTime)
//        workShiftService.findAllByBetweenStartTimeAndEndTime()

//        workShiftService.findAll(Example.of(probe))
//        workShiftService.findAll(Sort.by('startTime'))
    }

}
