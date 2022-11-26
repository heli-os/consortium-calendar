package kr.dataportal.calendar.organization.usecase

import kr.dataportal.calendar.organization.domain.OrganizationMember

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
interface JoinOrganizationUseCase {

    fun command(command: Command): OrganizationMember

    data class Command(
        val accountId: Long,
        val organizationId: Long,
    )
}
