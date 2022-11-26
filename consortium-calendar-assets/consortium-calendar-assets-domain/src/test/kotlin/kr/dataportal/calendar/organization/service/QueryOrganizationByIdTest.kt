package kr.dataportal.calendar.organization.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kr.dataportal.calendar.organization.OrganizationState
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationException
import kr.dataportal.calendar.organization.usecase.QueryOrganizationByIdUseCase
import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import kr.dataportal.calendar.persistence.repository.organization.OrganizationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@ExtendWith(MockKExtension::class)
internal class QueryOrganizationByIdTest {

    @MockK
    private lateinit var organizationRepository: OrganizationRepository

    private lateinit var sut: QueryOrganizationByIdUseCase

    @BeforeEach
    fun init() {
        sut = QueryOrganizationById(
            organizationRepository = organizationRepository
        )
    }

    private val dummy = QueryOrganizationByIdUseCase.Query(
        organizationId = 1L
    )

    private val organizationJpaEntity = OrganizationJpaEntity(
        name = "Organization"
    ).apply {
        id = 1L
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    @Test
    fun `단체 ID로 조회 실패 시 NotFoundOrganizationException 예외 발생`() {
        every { organizationRepository.findByIdOrNull(dummy.organizationId) } returns null

        expectThrows<NotFoundOrganizationException> { sut.query(query = dummy) }
    }

    @Test
    fun `단체 ID로 조회 성공`() {
        every { organizationRepository.findByIdOrNull(dummy.organizationId) } returns organizationJpaEntity

        val actual = sut.query(query = dummy)

        expectThat(actual) {
            get { id } isEqualTo 1L
            get { name } isEqualTo "Organization"
            get { state } isEqualTo OrganizationState.ACTIVATE
        }
    }
}
