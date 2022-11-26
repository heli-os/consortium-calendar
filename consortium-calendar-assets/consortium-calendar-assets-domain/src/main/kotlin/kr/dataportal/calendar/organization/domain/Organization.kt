package kr.dataportal.calendar.organization.domain

import kr.dataportal.calendar.config.BaseDomain
import kr.dataportal.calendar.organization.OrganizationState

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
data class Organization(
    val name: String,
    val state: OrganizationState
) : BaseDomain()
