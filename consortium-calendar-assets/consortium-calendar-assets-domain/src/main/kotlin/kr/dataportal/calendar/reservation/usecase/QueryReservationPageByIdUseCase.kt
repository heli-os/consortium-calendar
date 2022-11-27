package kr.dataportal.calendar.reservation.usecase

import kr.dataportal.calendar.reservation.domain.ReservationPage

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
interface QueryReservationPageByIdUseCase {

    fun query(query: Query): ReservationPage

    data class Query(
        val reservationPageId: Long
    )
}
