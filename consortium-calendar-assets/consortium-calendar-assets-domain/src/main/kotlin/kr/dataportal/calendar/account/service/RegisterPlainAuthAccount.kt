package kr.dataportal.calendar.account.service

import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.account.domain.Account
import kr.dataportal.calendar.account.exception.AlreadyRegisteredEmailException
import kr.dataportal.calendar.account.usecase.RegisterPlainAuthAccountUseCase
import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.hashSHA512
import kr.dataportal.calendar.persistence.entity.account.AccountAuthenticationJpaEntity
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountAuthenticationRepository
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** * @author Heli
 * Created on 2022. 11. 26
 */
@Service
@Transactional
class RegisterPlainAuthAccount(
    private val accountRepository: AccountRepository,
    private val accountAuthenticationRepository: AccountAuthenticationRepository
) : RegisterPlainAuthAccountUseCase {

    override fun command(command: RegisterPlainAuthAccountUseCase.Command): Account {
        val (name, email, phoneNumber, password) = command

        accountRepository.findByEmail(email = email)?.let { throw AlreadyRegisteredEmailException(email) }

        val accountJpaEntity = AccountJpaEntity(name = name, email = email, phoneNumber = phoneNumber)
            .also(accountRepository::save)

        val accountAuthenticationJpaEntity = AccountAuthenticationJpaEntity(
            authType = AccountAuthenticationType.PLAIN,
            authText = hashSHA512(password),
            accountJpaEntity = accountJpaEntity
        ).also(accountAuthenticationRepository::save)

        return Account(
            name = accountJpaEntity.name,
            email = accountJpaEntity.email,
            phoneNumber = accountJpaEntity.phoneNumber,
            authType = accountAuthenticationJpaEntity.authType
        ).entityData(accountJpaEntity)
    }
}
