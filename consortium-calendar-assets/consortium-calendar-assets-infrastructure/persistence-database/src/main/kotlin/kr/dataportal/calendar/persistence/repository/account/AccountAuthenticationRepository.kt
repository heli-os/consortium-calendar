package kr.dataportal.calendar.persistence.repository.account

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.persistence.entity.account.AccountAuthenticationJpaEntity
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface AccountAuthenticationRepository : JpaRepository<AccountAuthenticationJpaEntity, Long>,
    AccountAuthenticationRepositoryCustom

interface AccountAuthenticationRepositoryCustom {
    fun fetchByEmailAndAuthTypeAndAuthText(
        email: String,
        authType: AccountAuthenticationType,
        authText: String
    ): AccountAuthenticationJpaEntity?
}

class AccountAuthenticationRepositoryCustomImpl(
    private val queryFactory: SpringDataQueryFactory
) : AccountAuthenticationRepositoryCustom {

    override fun fetchByEmailAndAuthTypeAndAuthText(
        email: String,
        authType: AccountAuthenticationType,
        authText: String
    ): AccountAuthenticationJpaEntity? =
        queryFactory.singleQuery {
            select(entity(AccountAuthenticationJpaEntity::class))
            from(entity(AccountAuthenticationJpaEntity::class))
            fetch(AccountAuthenticationJpaEntity::accountJpaEntity)
            whereAnd(
                col(AccountJpaEntity::email).equal(email),
                col(AccountAuthenticationJpaEntity::authType).equal(authType),
                col(AccountAuthenticationJpaEntity::authText).equal(authText)
            )
        }
}
