package kr.dataportal.calendar.persistence.repository.reservation

import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
interface ReservationPageRepository : JpaRepository<ReservationPageJpaEntity, Long> {

    fun findByOrganizationJpaEntityId(organizationId: Long): ReservationPageJpaEntity?
}
