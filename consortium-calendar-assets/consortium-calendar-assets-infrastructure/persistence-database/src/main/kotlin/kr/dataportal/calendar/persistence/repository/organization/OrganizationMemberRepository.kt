package kr.dataportal.calendar.persistence.repository.organization

import kr.dataportal.calendar.persistence.entity.organization.OrganizationMemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface OrganizationMemberRepository : JpaRepository<OrganizationMemberJpaEntity, Long> {

    fun findAllByOrganizationJpaEntityId(organizationId: Long): List<OrganizationMemberJpaEntity>

    fun findByOrganizationJpaEntityIdAndAccountJpaEntityId(
        organizationId: Long,
        accountId: Long
    ): OrganizationMemberJpaEntity?
}
