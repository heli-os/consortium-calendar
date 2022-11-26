package kr.dataportal.calendar.organization.exception

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
class NotFoundOrganizationException(
    organizationId: Long
) : RuntimeException("단체 조회 실패 [organizationId=$organizationId]")
