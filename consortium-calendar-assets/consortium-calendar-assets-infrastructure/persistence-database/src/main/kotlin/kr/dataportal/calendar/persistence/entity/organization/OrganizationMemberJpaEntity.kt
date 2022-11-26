package kr.dataportal.calendar.persistence.entity.organization

import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Entity
@Table(name = "organization_member")
class OrganizationMemberJpaEntity(
    @ManyToOne
    @JoinColumn(name = "organization_id")
    val organizationJpaEntity: OrganizationJpaEntity,

    @ManyToOne
    @JoinColumn(name = "account_id")
    val accountJpaEntity: AccountJpaEntity
) : BaseEntity()
