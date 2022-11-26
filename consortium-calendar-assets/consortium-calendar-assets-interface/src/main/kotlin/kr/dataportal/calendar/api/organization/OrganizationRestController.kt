package kr.dataportal.calendar.api.organization

import kr.dataportal.calendar.organization.domain.Organization
import kr.dataportal.calendar.organization.usecase.CreateOrganizationUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@RestController
class OrganizationRestController(
    private val createOrganizationUseCase: CreateOrganizationUseCase
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
}
