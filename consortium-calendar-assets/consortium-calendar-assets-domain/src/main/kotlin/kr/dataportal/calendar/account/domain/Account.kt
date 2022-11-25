package kr.dataportal.calendar.account.domain

import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.config.BaseDomain

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
data class Account(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val authType: AccountAuthenticationType
) : BaseDomain()
