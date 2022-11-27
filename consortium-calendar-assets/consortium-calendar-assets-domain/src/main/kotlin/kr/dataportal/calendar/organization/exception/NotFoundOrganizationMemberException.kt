package kr.dataportal.calendar.organization.exception

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
class NotFoundOrganizationMemberException(
    organizationId: Long,
    accountId: Long
) : RuntimeException("단체에 가입되어 있지 않음 [organizationId=$organizationId, accountId=$accountId]")
