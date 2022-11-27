package kr.dataportal.calendar.api.reservation

import kr.dataportal.calendar.config.requiredId
import kr.dataportal.calendar.reservation.ReservationPageOption
import kr.dataportal.calendar.reservation.ReservationPageState
import kr.dataportal.calendar.reservation.domain.ReservationPage
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
data class CreateReservationPageDto(
    @field:NotBlank
    val title: String,
    @field:NotNull
    val organizationId: Long,
    @field:NotNull
    val option: ReservationPageOption
)

data class ReservationPageResponseDto(
    val id: Long,
    val title: String,
    val option: ReservationPageOption,
    val state: ReservationPageState,
    val organizationId: Long
)

// ====================================
internal fun ReservationPage.toResponseDto() = ReservationPageResponseDto(
    id = requiredId,
    title = title,
    option = option,
    state = state,
    organizationId = organizationId
)
