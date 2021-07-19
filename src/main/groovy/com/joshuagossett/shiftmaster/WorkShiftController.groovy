package com.joshuagossett.shiftmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.EntityResponse

@RestController
@RequestMapping(path = "/WorkShift", produces = [MediaType.APPLICATION_JSON_VALUE])
class WorkShiftController {
    @Autowired
    WorkShiftService workShiftService

    @GetMapping
    def index() {
       workShiftService.findAll()
    }

}
