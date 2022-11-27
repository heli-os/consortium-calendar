package kr.dataportal.calendar.reservation.usecase

import kr.dataportal.calendar.reservation.ReservationPageOption
import kr.dataportal.calendar.reservation.domain.ReservationPage

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
interface CreateReservationPageUseCase {

    fun command(command: Command): ReservationPage

    data class Command(
        val title: String,
        val accountId: Long,
        val organizationId: Long,
        val option: ReservationPageOption
    )
}
