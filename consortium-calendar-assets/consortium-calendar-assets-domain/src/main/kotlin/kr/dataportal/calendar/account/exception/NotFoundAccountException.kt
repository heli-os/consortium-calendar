package kr.dataportal.calendar.account.exception

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
class NotFoundAccountException(
    accountId: Long
) : RuntimeException("계정 조회 실패 [accountId=$accountId]")
