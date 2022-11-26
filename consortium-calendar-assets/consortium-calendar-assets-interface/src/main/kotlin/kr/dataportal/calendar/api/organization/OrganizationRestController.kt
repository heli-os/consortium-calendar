package kr.dataportal.calendar.api.organization

import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.usecase.CreateOrganizationUseCase
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
    private val queryOrganizationByIdUseCase: QueryOrganizationByIdUseCase
) {

    companion object {
        private const val ACCOUNT_HEADER_NAME = "X-ACCOUNT-ID"
    }

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
}
