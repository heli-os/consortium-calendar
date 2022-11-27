package kr.dataportal.calendar.api.reservation

import kr.dataportal.calendar.config.auditing.Auditing
import kr.dataportal.calendar.reservation.usecase.CreateReservationPageUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@RestController
class ReservationRestController(
    private val createReservationPageUseCase: CreateReservationPageUseCase
) {

    @PostMapping("/api/v1/reservation-page")
    fun createReservationPage(
        @RequestHeader(value = Auditing.ACCOUNT_HEADER_NAME) accountId: Long,
        @RequestBody @Valid dto: CreateReservationPageDto
    ): ReservationPageResponseDto =
        createReservationPageUseCase.command(
            command = CreateReservationPageUseCase.Command(
                title = dto.title,
                accountId = accountId,
                organizationId = dto.organizationId,
                option = dto.option
            )
        ).toResponseDto()
}
