package kr.dataportal.calendar.reservation.service

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kr.dataportal.calendar.persistence.service.reservation.ReservationPageQuery
import kr.dataportal.calendar.reservation.usecase.QueryReservationPageByIdUseCase
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @author Heli
 * Created on 2022. 11. 28
 */
@ExtendWith(MockKExtension::class)
internal class QueryReservationPageByIdTest {

    @MockK
    private lateinit var reservationPageQuery: ReservationPageQuery

    private lateinit var sut: QueryReservationPageByIdUseCase
}
