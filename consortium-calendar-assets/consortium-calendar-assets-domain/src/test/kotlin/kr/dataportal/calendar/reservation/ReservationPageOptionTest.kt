package kr.dataportal.calendar.reservation

import kr.dataportal.calendar.util.toJson
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
internal class ReservationPageOptionTest {
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

    private val sut = ReservationPageOption.Converter()

    @Test
    fun `covertToDatabaseColumn`() {
        val actual = sut.convertToDatabaseColumn(defaultOption)
        expectThat(actual) isEqualTo defaultOption.toJson()
    }

    @Test
    fun `convertToEntityAttribute`() {
        val actual = sut.convertToEntityAttribute(defaultOption.toJson())
        expectThat(actual.reserveSlotCondition) {
            get { durationMinute } isEqualTo 60L
            get { beforeBufferMinute } isEqualTo 0L
            get { afterBufferMinute } isEqualTo 0L
        }
        expectThat(actual.assignCondition) {
            get { possible } isEqualTo ReservationPageOption.AssignCondition.Possible.ALL
        }
        expectThat(actual.publishCondition) {
            get { reserveAllowNumber } isEqualTo null
            get { startAt } isEqualTo null
            get { endAt } isEqualTo null
        }
    }
}
