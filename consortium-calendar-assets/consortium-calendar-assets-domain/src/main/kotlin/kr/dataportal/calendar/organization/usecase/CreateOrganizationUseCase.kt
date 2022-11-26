package kr.dataportal.calendar.organization.usecase

import kr.dataportal.calendar.organization.domain.Organization

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface CreateOrganizationUseCase {

    fun command(command: Command): Organization

    data class Command(
        val name: String,
        val accountId: Long
    )
}
