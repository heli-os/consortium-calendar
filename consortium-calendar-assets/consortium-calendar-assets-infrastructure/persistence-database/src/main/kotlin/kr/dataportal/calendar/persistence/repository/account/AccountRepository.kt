package kr.dataportal.calendar.persistence.repository.account

import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface AccountRepository : JpaRepository<AccountJpaEntity, Long> {
    fun findByEmail(email: String): AccountJpaEntity?
}
