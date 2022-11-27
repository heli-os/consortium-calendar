package kr.dataportal.calendar.config.init

import kr.dataportal.calendar.api.account.AccountRestController
import kr.dataportal.calendar.api.account.LoginPlainAuthAccountDto
import kr.dataportal.calendar.api.account.RegisterPlainAuthAccountDto
import kr.dataportal.calendar.api.organization.CreateOrganizationDto
import kr.dataportal.calendar.api.organization.OrganizationRestController
import kr.dataportal.calendar.api.reservation.CreateReservationPageDto
import kr.dataportal.calendar.api.reservation.ReservationRestController
import kr.dataportal.calendar.reservation.ReservationPageOption
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
    private val organizationRestController: OrganizationRestController,
    private val reservationRestController: ReservationRestController
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

        accountRestController.registerPlainAuthAccount(
            dto = RegisterPlainAuthAccountDto(
                name = "Heli",
                email = "heli2@example.com",
                phoneNumber = "010-1111-2222",
                password = "password"
            )
        )

        accountRestController.registerPlainAuthAccount(
            dto = RegisterPlainAuthAccountDto(
                name = "Heli",
                email = "heli3@example.com",
                phoneNumber = "010-1111-2222",
                password = "password"
            )
        )

        val accountFirst = accountRestController.loginPlainAuthAccount(
            dto = LoginPlainAuthAccountDto(
                email = "heli@example.com",
                password = "password"
            )
        )

        val accountSecond = accountRestController.loginPlainAuthAccount(
            dto = LoginPlainAuthAccountDto(
                email = "heli2@example.com",
                password = "password"
            )
        )

        val accountThird = accountRestController.loginPlainAuthAccount(
            dto = LoginPlainAuthAccountDto(
                email = "heli3@example.com",
                password = "password"
            )
        )

        val organization = organizationRestController.createOrganization(
            accountId = accountFirst.id,
            dto = CreateOrganizationDto(
                name = "workspace"
            )
        )

        val existedOrganization = organizationRestController.organization(organizationId = organization.id)

        val joinedOrganization = organizationRestController.joinOrganization(
            accountId = accountSecond.id,
            organizationId = existedOrganization.id
        )

        val reservationPageOption = ReservationPageOption(
            reserveSlotCondition = ReservationPageOption.ReserveSlotCondition(
                durationMinute = 60L
            ),
            assignCondition = ReservationPageOption.AssignCondition(
                possible = ReservationPageOption.AssignCondition.Possible.ALL
            ),
            publishCondition = ReservationPageOption.PublishCondition(
                reserveAllowNumber = null
            )
        )

        val reservationPage = reservationRestController.createReservationPage(
            accountId = accountFirst.id,
            dto = CreateReservationPageDto(
                title = "reservation page",
                organizationId = existedOrganization.id,
                option = reservationPageOption
            )
        )

        val newOrganization = organizationRestController.createOrganization(
            accountId = accountSecond.id,
            dto = CreateOrganizationDto(
                name = "workspace"
            )
        )

        val existReservationPage = reservationRestController.reservationPage(
            reservationPageId = reservationPage.id
        )
    }
}
