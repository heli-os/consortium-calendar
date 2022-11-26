package kr.dataportal.calendar.api.organization

import kr.dataportal.calendar.config.requiredId
import kr.dataportal.calendar.organization.OrganizationState
import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.domain.OrganizationMember
import javax.validation.constraints.NotBlank

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
data class CreateOrganizationDto(
    @field:NotBlank
    val name: String
)

data class OrganizationResponseDto(
    val id: Long,
    val name: String,
    val state: OrganizationState
)

data class OrganizationMemberResponseDto(
    val organizationId: Long,
    val memberIds: List<Long>
)

// ====================================
internal fun Organization.toResponseDto() = OrganizationResponseDto(
    id = requiredId,
    name = name,
    state = state
)

internal fun OrganizationMember.toResponseDto() = OrganizationMemberResponseDto(
    organizationId = organizationId,
    memberIds = memberIds
)
