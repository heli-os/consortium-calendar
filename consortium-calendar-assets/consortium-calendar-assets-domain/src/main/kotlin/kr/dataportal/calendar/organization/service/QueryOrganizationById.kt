package kr.dataportal.calendar.organization.service

import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationException
import kr.dataportal.calendar.organization.usecase.QueryOrganizationByIdUseCase
import kr.dataportal.calendar.persistence.repository.organization.OrganizationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@Service
class QueryOrganizationById(
    private val organizationRepository: OrganizationRepository
) : QueryOrganizationByIdUseCase {

    override fun query(query: QueryOrganizationByIdUseCase.Query): Organization {
        val (organizationId) = query

        val organizationJpaEntity = organizationRepository.findByIdOrNull(id = organizationId)
            ?: throw NotFoundOrganizationException(organizationId)

        return Organization(
            name = organizationJpaEntity.name,
            state = organizationJpaEntity.state
        ).entityData(organizationJpaEntity)
    }
}
