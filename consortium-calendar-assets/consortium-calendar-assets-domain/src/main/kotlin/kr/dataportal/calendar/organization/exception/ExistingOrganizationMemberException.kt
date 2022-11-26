package kr.dataportal.calendar.organization.exception

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
class ExistingOrganizationMemberException(
    organizationId: Long,
    accountId: Long
) : RuntimeException("이미 가입된 단체 [organizationId=$organizationId, accountId=$accountId]")

