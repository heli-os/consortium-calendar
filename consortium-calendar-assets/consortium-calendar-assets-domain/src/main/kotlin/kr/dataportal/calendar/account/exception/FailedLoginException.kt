package kr.dataportal.calendar.account.exception

import kr.dataportal.calendar.account.AccountAuthenticationType

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
class FailedLoginException(
    email: String,
    authType: AccountAuthenticationType
) : RuntimeException("로그인 실패. [email=$email, authType=${authType.name}]")
