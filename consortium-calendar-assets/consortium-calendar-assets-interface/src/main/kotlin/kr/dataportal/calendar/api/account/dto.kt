package kr.dataportal.calendar.api.account

import kr.dataportal.calendar.account.domain.Account
import kr.dataportal.calendar.config.requiredId
import javax.validation.constraints.NotBlank

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
data class RegisterPlainAuthAccountDto(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val phoneNumber: String,
    @field:NotBlank
    val password: String
)

data class LoginPlainAuthAccountDto(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)

data class AccountResponseDto(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String
)


// ====================================
internal fun Account.toResponseDto() = AccountResponseDto(
    id = requiredId,
    name = name,
    email = email,
    phoneNumber = phoneNumber
)
