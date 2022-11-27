package kr.dataportal.calendar.reservation.domain

import kr.dataportal.calendar.config.BaseDomain
import kr.dataportal.calendar.reservation.ReservationPageOption
import kr.dataportal.calendar.reservation.ReservationPageState

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
data class ReservationPage(
    val title: String,
    val option: ReservationPageOption,
    val state: ReservationPageState,
    val organizationId: Long
) : BaseDomain()
