package kr.dataportal.calendar.organization.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.organization.OrganizationState
import kr.dataportal.calendar.organization.usecase.CreateOrganizationUseCase
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
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@ExtendWith(MockKExtension::class)
internal class CreateOrganizationTest {
    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var organizationRepository: OrganizationRepository

    @MockK
    private lateinit var organizationMemberRepository: OrganizationMemberRepository

    private lateinit var sut: CreateOrganizationUseCase

    private val organizationSlot = slot<OrganizationJpaEntity>()
    private val organizationMemberSlot = slot<OrganizationMemberJpaEntity>()


    @BeforeEach
    fun init() {
        val now = LocalDateTime.now()
        every {
            organizationRepository.save(capture(organizationSlot))
        } answers {
            organizationSlot.captured.apply {
                id = 1L
                createdAt = now
                lastModifiedAt = now
            }
        }

        every {
            organizationMemberRepository.save(capture(organizationMemberSlot))
        } answers {
            organizationMemberSlot.captured.apply {
                id = 10L
                createdAt = now
                lastModifiedAt = now
            }
        }

        sut = CreateOrganization(
            accountRepository = accountRepository,
            organizationRepository = organizationRepository,
            organizationMemberRepository = organizationMemberRepository
        )
    }

    private val dummy = CreateOrganizationUseCase.Command(
        name = "DataPortal.KR",
        accountId = 1L
    )

    private val organizationJpaEntity = OrganizationJpaEntity(
        name = dummy.name
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val accountJpaEntity = AccountJpaEntity(
        name = "Heli",
        email = "heli@example.com",
        phoneNumber = "010-1111-2222"
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    private val organizationMemberJpaEntity = OrganizationMemberJpaEntity(
        organizationJpaEntity = organizationJpaEntity,
        accountJpaEntity = accountJpaEntity
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    @Test
    fun `존재하지 않는 계정으로 생성 시도 시 NotFoundAccountException 예외 발생`() {
        every { accountRepository.findByIdOrNull(id = 1L) } returns null

        assertThrows<NotFoundAccountException> { sut.command(command = dummy) }
    }

    @Test
    fun `신규 단체 생성 성공`() {
        every { accountRepository.findByIdOrNull(id = 1L) } returns accountJpaEntity

        val actual = sut.command(command = dummy)

        expectThat(actual) {
            get { id } isEqualTo 1
            get { name } isEqualTo "DataPortal.KR"
            get { state } isEqualTo OrganizationState.ACTIVATE
        }
    }
}
