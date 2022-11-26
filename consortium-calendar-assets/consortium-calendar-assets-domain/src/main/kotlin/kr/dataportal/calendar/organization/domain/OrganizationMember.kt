package kr.dataportal.calendar.organization.domain

import kr.dataportal.calendar.config.BaseDomain

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
data class OrganizationMember(
    val organizationId: Long,
    val memberIds: List<Long>
) : BaseDomain()
