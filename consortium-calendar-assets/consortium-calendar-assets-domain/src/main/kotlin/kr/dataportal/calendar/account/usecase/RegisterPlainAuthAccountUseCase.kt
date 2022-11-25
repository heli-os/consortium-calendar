package kr.dataportal.calendar.account.usecase

import kr.dataportal.calendar.account.domain.Account

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface RegisterPlainAuthAccountUseCase {

    fun command(command: Command): Account

    data class Command(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val password: String
    )
}
