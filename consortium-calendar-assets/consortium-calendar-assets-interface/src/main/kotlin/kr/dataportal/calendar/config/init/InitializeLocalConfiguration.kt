package kr.dataportal.calendar.config.init

import kr.dataportal.calendar.api.account.AccountRestController
import kr.dataportal.calendar.api.account.LoginPlainAuthAccountDto
import kr.dataportal.calendar.api.account.RegisterPlainAuthAccountDto
import kr.dataportal.calendar.api.organization.CreateOrganizationDto
import kr.dataportal.calendar.api.organization.OrganizationRestController
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@Profile("local")
@Configuration
class InitializeLocalConfiguration(
    private val accountRestController: AccountRestController,
    private val organizationRestController: OrganizationRestController
) {

    @PostConstruct
    fun init() {
        accountRestController.registerPlainAuthAccount(
            dto = RegisterPlainAuthAccountDto(
                name = "Heli",
                email = "heli@example.com",
                phoneNumber = "010-1111-2222",
                password = "password"
            )
        )

        val account = accountRestController.loginPlainAuthAccount(
            dto = LoginPlainAuthAccountDto(
                email = "heli@example.com",
                password = "password"
            )
        )

        val organization = organizationRestController.createOrganization(
            accountId = account.id,
            dto = CreateOrganizationDto(
                name = "workspace"
            )
        )

        val existedOrganization = organizationRestController.organization(organizationId = organization.id)
    }
}
