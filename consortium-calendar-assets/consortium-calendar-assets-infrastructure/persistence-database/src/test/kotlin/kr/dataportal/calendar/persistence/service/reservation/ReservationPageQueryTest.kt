package kr.dataportal.calendar.persistence.service.reservation

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import kr.dataportal.calendar.persistence.repository.reservation.ReservationPageRepository
import kr.dataportal.calendar.persistence.service.reservation.exception.NotFoundReservationPageException
import kr.dataportal.calendar.reservation.ReservationPageOption
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
@ExtendWith(MockKExtension::class)
internal class ReservationPageQueryTest {

    @MockK
    private lateinit var reservationPageRepository: ReservationPageRepository

    private lateinit var sut: ReservationPageQuery

    @BeforeEach
    fun init() {
        sut = ReservationPageQuery(
            reservationPageRepository = reservationPageRepository
        )
    }

    private val reservationPageJpaEntity = ReservationPageJpaEntity(
        title = "reservation page",
        organizationJpaEntity = mockk(),
        option = ReservationPageOption(
            reserveSlotCondition = ReservationPageOption.ReserveSlotCondition(
                durationMinute = 60L
            ),
            assignCondition = ReservationPageOption.AssignCondition(
                possible = ReservationPageOption.AssignCondition.Possible.ALL
            ),
            publishCondition = ReservationPageOption.PublishCondition(
                reserveAllowNumber = null
            )
        )
    ).apply { id = 1L }

    @Nested
    inner class FetchById {
        @Test
        fun `fetch 성공 시 Entity 반환`() {
            every { reservationPageRepository.fetchById(any()) } returns reservationPageJpaEntity

            val actual = sut.fetchById(reservationPageId = 1L)

            expectThat(actual) {
                get { id } isEqualTo 1L
                get { title } isEqualTo "reservation page"
                expectThat(actual.option.reserveSlotCondition) {
                    get { durationMinute } isEqualTo 60L
                }
                expectThat(actual.option.assignCondition) {
                    get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ALL
                }
                expectThat(actual.option.publishCondition) {
                    get { reserveAllowNumber } isEqualTo null
                }
            }
        }

        @Test
        fun `fetch 실패 시 NotFoundReservationPageException 예외 발생`() {
            every { reservationPageRepository.fetchById(any()) } returns null

            expectThrows<NotFoundReservationPageException> {
                sut.fetchById(reservationPageId = 1L)
            }
        }
    }
}
