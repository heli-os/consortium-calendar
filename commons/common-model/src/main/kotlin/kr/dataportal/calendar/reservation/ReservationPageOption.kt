package kr.dataportal.calendar.reservation

import kr.dataportal.calendar.util.parseJson
import kr.dataportal.calendar.util.toJson
import java.time.LocalDateTime
import javax.persistence.AttributeConverter

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
data class ReservationPageOption(
    var reserveSlotCondition: ReserveSlotCondition,
    var assignCondition: AssignCondition,
    var publishCondition: PublishCondition
) {

    data class ReserveSlotCondition(
        var durationMinute: Long,
        var beforeBufferMinute: Long = 0, // TODO not used
        var afterBufferMinute: Long = 0, // TODO not used
    )

    data class AssignCondition(
        val possible: Possible
    ) {
        enum class Possible {
            ALL,
            ONE_OF_ALL
        }
    }

    data class PublishCondition(
        var reserveAllowNumber: Long? = null,
        var startAt: LocalDateTime? = null, // TODO not used
        var endAt: LocalDateTime? = null // TODO not used
    )

    class Converter : AttributeConverter<ReservationPageOption, String> {
        override fun convertToDatabaseColumn(attribute: ReservationPageOption): String = attribute.toJson()

        override fun convertToEntityAttribute(dbData: String): ReservationPageOption =
            dbData.parseJson(ReservationPageOption::class.java)
    }
}
