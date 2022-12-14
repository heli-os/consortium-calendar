package kr.dataportal.calendar.api.account

import kr.dataportal.calendar.account.domain.Account
import kr.dataportal.calendar.account.usecase.LoginPlainAuthAccountUseCase
import kr.dataportal.calendar.account.usecase.RegisterPlainAuthAccountUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@RestController
class AccountRestController(
    private val registerPlainAuthAccountUseCase: RegisterPlainAuthAccountUseCase,
    private val loginPlainAuthAccountUseCase: LoginPlainAuthAccountUseCase
) {

    @PostMapping("/api/v1/account")
    fun registerPlainAuthAccount(
        @Valid @RequestBody dto: RegisterPlainAuthAccountDto
    ): AccountResponseDto =
        registerPlainAuthAccountUseCase.command(
            command = RegisterPlainAuthAccountUseCase.Command(
                name = dto.name,
                email = dto.email,
                phoneNumber = dto.phoneNumber,
                password = dto.password
            )
        ).let(Account::toResponseDto)

    @PostMapping("/api/v1/account/login")
    fun loginPlainAuthAccount(
        @Valid @RequestBody dto: LoginPlainAuthAccountDto
    ): AccountResponseDto =
        loginPlainAuthAccountUseCase.query(
            query = LoginPlainAuthAccountUseCase.Query(
                email = dto.email,
                password = dto.password
            )
        ).let(Account::toResponseDto)
}
