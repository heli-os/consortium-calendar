package kr.dataportal.calendar.account.exception

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
class AlreadyRegisteredEmailException(email: String) : RuntimeException("이미 가입된 이메일입니다. [email=$email]")
