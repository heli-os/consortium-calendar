package kr.dataportal.calendar.api.reservation

import kr.dataportal.calendar.config.auditing.Auditing
import kr.dataportal.calendar.reservation.usecase.CreateReservationPageUseCase
import kr.dataportal.calendar.reservation.usecase.QueryReservationPageByIdUseCase
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@RestController
class ReservationRestController(
    private val createReservationPageUseCase: CreateReservationPageUseCase,
    private val queryReservationPageByIdUseCase: QueryReservationPageByIdUseCase
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

    @GetMapping("/api/v1/reservation-page/{reservationPageId}")
    fun reservationPage(
        @PathVariable reservationPageId: Long
    ): ReservationPageResponseDto =
        queryReservationPageByIdUseCase.query(
            query = QueryReservationPageByIdUseCase.Query(
                reservationPageId = reservationPageId
            )
        ).toResponseDto()
}
