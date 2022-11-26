package kr.dataportal.calendar.account.service

import kr.dataportal.calendar.account.domain.Account
import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.account.usecase.QueryAccountByIdUseCase
import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Service
@Transactional(readOnly = true)
class QueryAccountById(
    private val accountRepository: AccountRepository
) : QueryAccountByIdUseCase {

    override fun query(query: QueryAccountByIdUseCase.Query): Account {
        val (accountId) = query

        val accountJpaEntity = accountRepository.findByIdOrNull(
            id = accountId
        ) ?: throw NotFoundAccountException(accountId)

        return Account(
            name = accountJpaEntity.name,
            email = accountJpaEntity.email,
            phoneNumber = accountJpaEntity.phoneNumber
        ).entityData(accountJpaEntity)
    }
}
