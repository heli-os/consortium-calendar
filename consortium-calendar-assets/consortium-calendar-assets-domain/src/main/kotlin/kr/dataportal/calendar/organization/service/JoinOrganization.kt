package kr.dataportal.calendar.organization.service

import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.organization.domain.OrganizationMember
import kr.dataportal.calendar.organization.exception.ExistingOrganizationMemberException
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationException
import kr.dataportal.calendar.organization.usecase.JoinOrganizationUseCase
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import kr.dataportal.calendar.persistence.entity.organization.OrganizationMemberJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationMemberRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@Service
@Transactional
class JoinOrganization(
    private val accountRepository: AccountRepository,
    private val organizationRepository: OrganizationRepository,
    private val organizationMemberRepository: OrganizationMemberRepository
) : JoinOrganizationUseCase {

    override fun command(command: JoinOrganizationUseCase.Command): OrganizationMember {
        val (accountId, organizationId) = command

        val accountJpaEntity = accountRepository.findByIdOrNull(
            id = accountId
        ) ?: throw NotFoundAccountException(accountId)

        val organizationJpaEntity = organizationRepository.findByIdOrNull(id = organizationId)
            ?: throw NotFoundOrganizationException(organizationId)

        organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
            organizationId = organizationJpaEntity.requiredId,
            accountId = accountJpaEntity.requiredId
        )?.let {
            throw ExistingOrganizationMemberException(
                organizationId = organizationJpaEntity.requiredId,
                accountId = accountJpaEntity.requiredId
            )
        }

        OrganizationMemberJpaEntity(
            accountJpaEntity = accountJpaEntity,
            organizationJpaEntity = organizationJpaEntity
        ).also(organizationMemberRepository::save)

        val organizationMemberJpaEntities = organizationMemberRepository.findAllByOrganizationJpaEntityId(
            organizationId = organizationJpaEntity.requiredId
        )

        return OrganizationMember(
            organizationId = organizationJpaEntity.requiredId,
            memberIds = organizationMemberJpaEntities.map { it.requiredId }
        )
    }
}
