package kr.dataportal.calendar.account.usecase

import kr.dataportal.calendar.account.domain.Account

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface QueryAccountByIdUseCase {

    fun query(query: Query): Account

    data class Query(
        val accountId: Long
    )
}
