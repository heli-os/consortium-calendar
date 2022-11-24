package kr.dataportal.calendar

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 * @author Heli
 * Created on 2022. 11. 25
 */
@WebMvcTest(controllers = [HealthCheckRestController::class])
@ContextConfiguration(classes = [WebApplicationConfigurationAware.FakeApplication::class])
internal class HealthCheckRestControllerTest {

    @Autowired
    private lateinit var sut: MockMvc

    @Test
    fun `ping pong`() {
        sut.perform(
            MockMvcRequestBuilders.get("/api/ping").accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("pong"))
    }
}
