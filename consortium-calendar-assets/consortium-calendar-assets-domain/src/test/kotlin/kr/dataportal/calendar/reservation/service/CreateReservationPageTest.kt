package kr.dataportal.calendar.reservation.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationMemberException
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import kr.dataportal.calendar.persistence.entity.organization.OrganizationJpaEntity
import kr.dataportal.calendar.persistence.entity.organization.OrganizationMemberJpaEntity
import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import kr.dataportal.calendar.persistence.repository.organization.OrganizationMemberRepository
import kr.dataportal.calendar.persistence.repository.reservation.ReservationPageRepository
import kr.dataportal.calendar.reservation.ReservationPageOption
import kr.dataportal.calendar.reservation.ReservationPageState
import kr.dataportal.calendar.reservation.exception.ExistingReservationPageException
import kr.dataportal.calendar.reservation.usecase.CreateReservationPageUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@ExtendWith(MockKExtension::class)
internal class CreateReservationPageTest {
    @MockK
    private lateinit var organizationMemberRepository: OrganizationMemberRepository

    @MockK
    private lateinit var reservationPageRepository: ReservationPageRepository

    private lateinit var sut: CreateReservationPageUseCase

    private val reservationPageSlot = slot<ReservationPageJpaEntity>()

    @BeforeEach
    fun init() {
        val now = LocalDateTime.now()

        every {
            reservationPageRepository.save(capture(reservationPageSlot))
        } answers {
            reservationPageSlot.captured.apply {
                id = id ?: 20L
                createdAt = now
                lastModifiedAt = now
            }
        }

        sut = CreateReservationPage(
            organizationMemberRepository = organizationMemberRepository,
            reservationPageRepository = reservationPageRepository
        )
    }

    private val defaultOption = ReservationPageOption(
        reserveSlotCondition = ReservationPageOption.ReserveSlotCondition(
            durationMinute = 60L,
            beforeBufferMinute = 0,
            afterBufferMinute = 0
        ),
        assignCondition = ReservationPageOption.AssignCondition(
            possible = ReservationPageOption.AssignCondition.Possible.ALL
        ),
        publishCondition = ReservationPageOption.PublishCondition(
            reserveAllowNumber = null,
            startAt = null,
            endAt = null
        )
    )

    private fun dummy(option: ReservationPageOption = defaultOption) = CreateReservationPageUseCase.Command(
        title = "예약 페이지",
        accountId = 1L,
        organizationId = 10L,
        option = option
    )

    private val organizationJpaEntity = OrganizationJpaEntity(
        name = "workspace"
    ).apply { id = dummy().organizationId }

    private val organizationMemberJpaEntity = OrganizationMemberJpaEntity(
        organizationJpaEntity = organizationJpaEntity,
        accountJpaEntity = mockk()
    ).apply { id = 2L }

    @Test
    fun `단체에 이미 예약 페이지가 존재하는 경우 ExistingReservationPageException 예외 발생`() {
        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns mockk()

        expectThrows<ExistingReservationPageException> { sut.command(command = dummy()) }
    }

    @Test
    fun `단체의 멤버가 아닌 경우 NotFoundOrganizationMemberException 예외 발생`() {
        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns null
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy().organizationId,
                accountId = dummy().accountId
            )
        } returns null

        expectThrows<NotFoundOrganizationMemberException> { sut.command(command = dummy()) }
    }

    @Test
    fun `예약 페이지 생성 성공 - default option`() {
        val option = defaultOption

        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns null
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy().organizationId,
                accountId = dummy().accountId
            )
        } returns organizationMemberJpaEntity

        val actual = sut.command(command = dummy(option))
        expectThat(actual) {
            get { id } isEqualTo 20L
            get { title } isEqualTo dummy().title
            get { state } isEqualTo ReservationPageState.ACTIVATE
            get { organizationId } isEqualTo organizationJpaEntity.requiredId
            expectThat(actual.option.reserveSlotCondition) {
                get { durationMinute } isEqualTo 60L
                get { beforeBufferMinute } isEqualTo 0L
                get { afterBufferMinute } isEqualTo 0L
            }
            expectThat(actual.option.assignCondition) {
                get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ALL
            }
            expectThat(actual.option.publishCondition) {
                get { reserveAllowNumber } isEqualTo null
                get { startAt } isEqualTo null
                get { endAt } isEqualTo null
            }
        }
    }

    @Test
    fun `예약 페이지 생성 성공 - duration minute is 101`() {
        val option = defaultOption.apply {
            reserveSlotCondition = ReservationPageOption.ReserveSlotCondition(durationMinute = 101L)
        }

        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns null
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy().organizationId,
                accountId = dummy().accountId
            )
        } returns organizationMemberJpaEntity

        val actual = sut.command(command = dummy(option))
        expectThat(actual) {
            get { id } isEqualTo 20L
            get { title } isEqualTo dummy().title
            get { state } isEqualTo ReservationPageState.ACTIVATE
            get { organizationId } isEqualTo organizationJpaEntity.requiredId
            expectThat(actual.option.reserveSlotCondition) {
                get { durationMinute } isEqualTo 101L
                get { beforeBufferMinute } isEqualTo 0L
                get { afterBufferMinute } isEqualTo 0L
            }
            expectThat(actual.option.assignCondition) {
                get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ALL
            }
            expectThat(actual.option.publishCondition) {
                get { reserveAllowNumber } isEqualTo null
                get { startAt } isEqualTo null
                get { endAt } isEqualTo null
            }
        }
    }

    @Test
    fun `예약 페이지 생성 성공 - possible is ONE_OF_ALL`() {
        val option = defaultOption.apply {
            assignCondition = ReservationPageOption.AssignCondition(
                possible = ReservationPageOption.AssignCondition.Possible.ONE_OF_ALL
            )
        }

        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns null
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy().organizationId,
                accountId = dummy().accountId
            )
        } returns organizationMemberJpaEntity

        val actual = sut.command(command = dummy(option))
        expectThat(actual) {
            get { id } isEqualTo 20L
            get { title } isEqualTo dummy().title
            get { state } isEqualTo ReservationPageState.ACTIVATE
            get { organizationId } isEqualTo organizationJpaEntity.requiredId
            expectThat(actual.option.reserveSlotCondition) {
                get { durationMinute } isEqualTo 60L
                get { beforeBufferMinute } isEqualTo 0L
                get { afterBufferMinute } isEqualTo 0L
            }
            expectThat(actual.option.assignCondition) {
                get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ONE_OF_ALL
            }
            expectThat(actual.option.publishCondition) {
                get { reserveAllowNumber } isEqualTo null
                get { startAt } isEqualTo null
                get { endAt } isEqualTo null
            }
        }
    }

    @Test
    fun `예약 페이지 생성 성공 - reserve allow number is 202`() {
        val option = defaultOption.apply {
            publishCondition = ReservationPageOption.PublishCondition(
                reserveAllowNumber = 202L
            )
        }

        every { reservationPageRepository.findByOrganizationJpaEntityId(organizationId = dummy().organizationId) } returns null
        every {
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = dummy().organizationId,
                accountId = dummy().accountId
            )
        } returns organizationMemberJpaEntity

        val actual = sut.command(command = dummy(option))
        expectThat(actual) {
            get { id } isEqualTo 20L
            get { title } isEqualTo dummy().title
            get { state } isEqualTo ReservationPageState.ACTIVATE
            get { organizationId } isEqualTo organizationJpaEntity.requiredId
            expectThat(actual.option.reserveSlotCondition) {
                get { durationMinute } isEqualTo 60L
                get { beforeBufferMinute } isEqualTo 0L
                get { afterBufferMinute } isEqualTo 0L
            }
            expectThat(actual.option.assignCondition) {
                get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ALL
            }
            expectThat(actual.option.publishCondition) {
                get { reserveAllowNumber } isEqualTo 202L
                get { startAt } isEqualTo null
                get { endAt } isEqualTo null
            }
        }
    }
}
