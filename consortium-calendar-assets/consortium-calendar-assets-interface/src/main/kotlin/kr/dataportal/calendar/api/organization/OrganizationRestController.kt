package kr.dataportal.calendar.api.organization

import kr.dataportal.calendar.config.auditing.Auditing.ACCOUNT_HEADER_NAME
import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.domain.OrganizationMember
import kr.dataportal.calendar.organization.usecase.CreateOrganizationUseCase
import kr.dataportal.calendar.organization.usecase.JoinOrganizationUseCase
import kr.dataportal.calendar.organization.usecase.QueryOrganizationByIdUseCase
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@RestController
class OrganizationRestController(
    private val createOrganizationUseCase: CreateOrganizationUseCase,
    private val queryOrganizationByIdUseCase: QueryOrganizationByIdUseCase,
    private val joinOrganizationUseCase: JoinOrganizationUseCase
) {

    @PostMapping("/api/v1/organization")
    fun createOrganization(
        @RequestHeader(value = ACCOUNT_HEADER_NAME) accountId: Long,
        @RequestBody @Valid dto: CreateOrganizationDto
    ): OrganizationResponseDto =
        createOrganizationUseCase.command(
            command = CreateOrganizationUseCase.Command(
                name = dto.name,
                accountId = accountId
            )
        ).let(Organization::toResponseDto)

    @GetMapping("/api/v1/organization/{organizationId}")
    fun organization(
        @PathVariable organizationId: Long
    ): OrganizationResponseDto =
        queryOrganizationByIdUseCase.query(
            query = QueryOrganizationByIdUseCase.Query(
                organizationId = organizationId
            )
        ).let(Organization::toResponseDto)

    @PostMapping("/api/v1/organization/{organizationId}/member")
    fun joinOrganization(
        @RequestHeader(value = ACCOUNT_HEADER_NAME) accountId: Long,
        @PathVariable organizationId: Long
    ): OrganizationMemberResponseDto =
        joinOrganizationUseCase.command(
            command = JoinOrganizationUseCase.Command(
                organizationId = organizationId,
                accountId = accountId
            )
        ).let(OrganizationMember::toResponseDto)
}
