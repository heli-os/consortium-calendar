package kr.dataportal.calendar.organization.service

import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.usecase.CreateOrganizationUseCase
import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import kr.dataportal.calendar.persistence.entity.organization.OrganizationMemberJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationMemberRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Service
@Transactional
class CreateOrganization(
    private val accountRepository: AccountRepository,
    private val organizationRepository: OrganizationRepository,
    private val organizationMemberRepository: OrganizationMemberRepository
) : CreateOrganizationUseCase {

    override fun command(command: CreateOrganizationUseCase.Command): Organization {
        val (name, accountId) = command

        val accountJpaEntity = accountRepository.findByIdOrNull(
            id = accountId
        ) ?: throw NotFoundAccountException(accountId)

        val organizationJpaEntity = OrganizationJpaEntity(name = name)
            .also(organizationRepository::save)

        OrganizationMemberJpaEntity(
            accountJpaEntity = accountJpaEntity,
            organizationJpaEntity = organizationJpaEntity
        ).also(organizationMemberRepository::save)

        return Organization(
            name = organizationJpaEntity.name,
            state = organizationJpaEntity.state
        ).entityData(organizationJpaEntity)
    }
}
