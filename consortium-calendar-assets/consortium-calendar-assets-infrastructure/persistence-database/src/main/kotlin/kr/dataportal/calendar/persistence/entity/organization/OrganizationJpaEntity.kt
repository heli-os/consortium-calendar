package kr.dataportal.calendar.persistence.entity.organization

import kr.dataportal.calendar.organization.OrganizationState
import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Entity
@Table(name = "organization")
class OrganizationJpaEntity(
    var name: String
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    var state: OrganizationState = OrganizationState.ACTIVATE
}
