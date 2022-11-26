package kr.dataportal.calendar.account.service

import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.account.domain.Account
import kr.dataportal.calendar.account.exception.FailedLoginException
import kr.dataportal.calendar.account.usecase.LoginPlainAuthAccountUseCase
import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.hashSHA512
import kr.dataportal.calendar.persistence.repository.account.AccountAuthenticationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Service
@Transactional(readOnly = true)
class LoginPlainAuthAccount(
    private val accountAuthenticationRepository: AccountAuthenticationRepository
) : LoginPlainAuthAccountUseCase {

    override fun query(query: LoginPlainAuthAccountUseCase.Query): Account {
        val (email, password) = query
        val accountAuthenticationJpaEntity = accountAuthenticationRepository.fetchByEmailAndAuthTypeAndAuthText(
            email = email,
            authType = AccountAuthenticationType.PLAIN,
            authText = hashSHA512(password)
        ) ?: throw FailedLoginException(email = email, authType = AccountAuthenticationType.PLAIN)

        return Account(
            name = accountAuthenticationJpaEntity.accountJpaEntity.name,
            email = accountAuthenticationJpaEntity.accountJpaEntity.email,
            phoneNumber = accountAuthenticationJpaEntity.accountJpaEntity.phoneNumber,
            authType = accountAuthenticationJpaEntity.authType
        ).entityData(accountAuthenticationJpaEntity)
    }
}
