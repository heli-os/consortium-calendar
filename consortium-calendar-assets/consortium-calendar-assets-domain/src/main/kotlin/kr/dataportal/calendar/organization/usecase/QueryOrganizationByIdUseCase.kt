package kr.dataportal.calendar.organization.usecase

import kr.dataportal.calendar.organization.domain.Organization

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
interface QueryOrganizationByIdUseCase {

    fun query(query: Query): Organization

    data class Query(
        val organizationId: Long
    )
}
