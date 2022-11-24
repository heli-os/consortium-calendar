package kr.dataportal.calendar

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Heli
 * Created on 2022. 11. 25
 */
@RestController
class HealthCheckRestController {

    @RequestMapping("/api/ping")
    fun ping() = "pong"
}
