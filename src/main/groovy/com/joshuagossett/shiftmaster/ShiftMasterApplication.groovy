package com.joshuagossett.shiftmaster

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class ShiftMasterApplication {

	static void main(String[] args) {
		SpringApplication.run(ShiftMasterApplication, args)
	}

}
