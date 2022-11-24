package kr.dataportal.calendar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * @author Heli
 * Created on 2022. 11. 25
 */
@SpringBootTest(classes = [WebApplicationConfigurationAware.FakeApplication::class])
@ActiveProfiles(profiles = ["test"])
class WebApplicationConfigurationAware {

    @SpringBootApplication
    class FakeApplication
}
