package kr.dataportal.calendar.persistence.service.reservation

import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import kr.dataportal.calendar.persistence.repository.reservation.ReservationPageRepository
import kr.dataportal.calendar.persistence.service.reservation.exception.NotFoundReservationPageException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
@Service
@Transactional(readOnly = true)
class ReservationPageQuery(
    private val reservationPageRepository: ReservationPageRepository
) {

    fun fetchById(reservationPageId: Long): ReservationPageJpaEntity =
        reservationPageRepository.fetchById(reservationPageId = reservationPageId)
            ?: throw NotFoundReservationPageException(reservationPageId = reservationPageId)

    fun findByOrganizationId(organizationId: Long): ReservationPageJpaEntity? =
        reservationPageRepository.findByOrganizationJpaEntityId(organizationId = organizationId)
}
