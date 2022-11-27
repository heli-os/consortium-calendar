package kr.dataportal.calendar.reservation.exception

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
class ExistingReservationPageException(
    organizationId: Long
) : RuntimeException("단체에 이미 예약 페이지가 존재 합니다. [organizationId=$organizationId]")
