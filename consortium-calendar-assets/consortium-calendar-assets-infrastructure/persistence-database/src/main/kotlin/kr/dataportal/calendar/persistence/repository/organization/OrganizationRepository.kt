package kr.dataportal.calendar.persistence.repository.organization

import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface OrganizationRepository : JpaRepository<OrganizationJpaEntity, Long>
