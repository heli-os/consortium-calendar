package kr.dataportal.calendar.persistence.entity.reservation

import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import kr.dataportal.calendar.reservation.ReservationPageOption
import kr.dataportal.calendar.reservation.ReservationPageState
import javax.persistence.*

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@Entity
@Table(name = "reservation_page")
class ReservationPageJpaEntity(
    var title: String,
    @OneToOne
    @JoinColumn(name = "organization_id")
    val organizationJpaEntity: OrganizationJpaEntity,
    @Convert(converter = ReservationPageOption.Converter::class)
    @Column(name = "reservation_page_option")
    var option: ReservationPageOption
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    var state: ReservationPageState = ReservationPageState.ACTIVATE
}
