package kr.dataportal.calendar.persistence.repository.reservation

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
interface ReservationPageRepository : JpaRepository<ReservationPageJpaEntity, Long>, ReservationPageRepositoryCustom {

    fun findByOrganizationJpaEntityId(organizationId: Long): ReservationPageJpaEntity?
}

interface ReservationPageRepositoryCustom {

    fun fetchById(reservationPageId: Long): ReservationPageJpaEntity?
}

class ReservationPageRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory
) : ReservationPageRepositoryCustom {

    override fun fetchById(reservationPageId: Long): ReservationPageJpaEntity? =
        queryFactory.singleQuery {
            select(entity(ReservationPageJpaEntity::class))
            from(entity(ReservationPageJpaEntity::class))
            fetch(ReservationPageJpaEntity::organizationJpaEntity)
            where(
                col(ReservationPageJpaEntity::id).equal(reservationPageId)
            )
        }
}
