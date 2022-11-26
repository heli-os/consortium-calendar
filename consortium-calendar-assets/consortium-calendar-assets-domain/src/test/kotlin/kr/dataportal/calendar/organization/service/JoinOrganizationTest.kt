package kr.dataportal.calendar.organization.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.organization.exception.ExistingOrganizationMemberException
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationException
import kr.dataportal.calendar.organization.usecase.JoinOrganizationUseCase
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import kr.dataportal.calendar.persistence.entity.organization.OrganizationMemberJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationMemberRepository
import kr.dataportal.calendar.persistence.repository.organization.OrganizationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@ExtendWith(MockKExtension::class)
internal class JoinOrganizationTest {
    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var organizationRepository: OrganizationRepository

    @MockK
    private lateinit var organizationMemberRepository: OrganizationMemberRepository

    private lateinit var sut: JoinOrganizationUseCase

    private val organizationMemberSlot = slot<OrganizationMemberJpaEntity>()

    @BeforeEach
    fun init() {
        val now = LocalDateTime.now()
        every {
            organizationMemberRepository.save(capture(organizationMemberSlot))
        } answers {
            organizationMemberSlot.captured.apply {
                id = 1L
                createdAt = now
                lastModifiedAt = now
            }
        }

        sut = JoinOrganization(
            accountRepository = accountRepository,
            organizationRepository = organizationRepository,
            organizationMemberRepository = organizationMemberRepository
        )
    }

    private val dummy = JoinOrganizationUseCase.Command(
        accountId = 1L,
        organizationId = 1L
    )

    private val organizationJpaEntity = OrganizationJpaEntity(
        name = "Heli"
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val accountJpaEntityFirst = AccountJpaEntity(
        name = "Heli",
        email = "heli@example.com",
        phoneNumber = "010-1111-2222"
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val accountJpaEntitySecond = AccountJpaEntity(
        name = "Heli",
        email = "heli2@example.com",
        phoneNumber = "010-1111-2222"
    ).apply {
        id = 2L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val organizationMemberJpaEntityFirst = OrganizationMemberJpaEntity(
        organizationJpaEntity = organizationJpaEntity,
        accountJpaEntity = accountJpaEntityFirst
    ).apply {
        id = 10L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val organizationMemberJpaEntitySecond = OrganizationMemberJpaEntity(
        organizationJpaEntity = organizationJpaEntity,
        accountJpaEntity = accountJpaEntitySecond
    ).apply {
        id = 20L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    @Test
    fun `존재하지 않는 계정으로 생성 시도 시 NotFoundAccountException 예외 발생`() {
        every { accountRepository.findByIdOrNull(id = dummy.accountId) } returns null

        assertThrows<NotFoundAccountException> { sut.command(command = dummy) }
    }

    @Test
    fun `존재하지 않는 단체에 가입 시도 시 NotFoundOrganizationException 예외 발생`() {
        every { accountRepository.findByIdOrNull(id = dummy.accountId) } returns accountJpaEntityFirst
        every { organizationRepository.findByIdOrNull(id = dummy.organizationId) } returns null

        assertThrows<NotFoundOrganizationException> { sut.command(command = dummy) }
    }

    @Test
    fun `이미 가입된 단체에 가입 시도 시 ExistingOrganizationMemberException 예외 발생`() {
        every { organizationRepository.findByIdOrNull(id = dummy.organizationId) } returns organizationJpaEntity
        every { accountRepository.findByIdOrNull(id = dummy.accountId) } returns accountJpaEntityFirst
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = organizationJpaEntity.requiredId,
                accountId = accountJpaEntityFirst.requiredId
            )
        } returns organizationMemberJpaEntityFirst
        assertThrows<ExistingOrganizationMemberException> { sut.command(command = dummy) }
    }

    @Test
    fun `신규 단체 생성 성공`() {
        every { accountRepository.findByIdOrNull(id = dummy.accountId) } returns accountJpaEntityFirst
        every { organizationRepository.findByIdOrNull(id = dummy.organizationId) } returns organizationJpaEntity
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = organizationJpaEntity.requiredId,
                accountId = accountJpaEntityFirst.requiredId
            )
        } returns null
        every {
            organizationMemberRepository.findAllByOrganizationJpaEntityId(
                organizationId = organizationJpaEntity.requiredId
            )
        } returns listOf(organizationMemberJpaEntityFirst, organizationMemberJpaEntitySecond)

        val actual = sut.command(command = dummy)

        expectThat(actual) {
            get { memberIds } hasSize 2
            get { memberIds } containsExactly listOf(10L, 20L)
        }
        verify(exactly = 1) { accountRepository.findByIdOrNull(id = dummy.accountId) }
        verify(exactly = 1) { organizationRepository.findByIdOrNull(id = dummy.organizationId) }
        verify(exactly = 1) {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy.organizationId,
                accountId = dummy.accountId
            )
        }
        verify(exactly = 1) {
            organizationMemberRepository.findAllByOrganizationJpaEntityId(
                organizationId = dummy.organizationId,
            )
        }
        verify(exactly = 1) { organizationMemberRepository.save(any()) }
    }
}
