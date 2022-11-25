package kr.dataportal.calendar.persistence.entity.account

import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import javax.persistence.*

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Entity
@Table(name = "account_authentication")
class AccountAuthenticationJpaEntity(
    @Enumerated(EnumType.STRING)
    val authType: AccountAuthenticationType,
    val authText: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val accountJpaEntity: AccountJpaEntity
) : BaseEntity()
