package kr.dataportal.calendar.reservation.service

import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import kr.dataportal.calendar.persistence.service.reservation.ReservationPageQuery
import kr.dataportal.calendar.reservation.domain.ReservationPage
import kr.dataportal.calendar.reservation.usecase.QueryReservationPageByIdUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
@Service
@Transactional(readOnly = true)
class QueryReservationPageById(
    private val reservationPageQuery: ReservationPageQuery
) : QueryReservationPageByIdUseCase {

    override fun query(query: QueryReservationPageByIdUseCase.Query): ReservationPage {
        val (reservationPageId) = query

        val reservationPageJpaEntity = reservationPageQuery.fetchById(reservationPageId = reservationPageId)

        return ReservationPage(
            title = reservationPageJpaEntity.title,
            option = reservationPageJpaEntity.option,
            state = reservationPageJpaEntity.state,
            organizationId = reservationPageJpaEntity.organizationJpaEntity.requiredId
        ).entityData(reservationPageJpaEntity)
    }
}
