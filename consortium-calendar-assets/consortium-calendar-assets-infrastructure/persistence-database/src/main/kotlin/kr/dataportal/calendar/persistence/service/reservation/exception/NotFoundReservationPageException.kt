package kr.dataportal.calendar.persistence.service.reservation.exception

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
class NotFoundReservationPageException(
    reservationPageId: Long
) : RuntimeException("존재하지 않는 단체 예약 페이지 [reservationPageId=$reservationPageId]")
