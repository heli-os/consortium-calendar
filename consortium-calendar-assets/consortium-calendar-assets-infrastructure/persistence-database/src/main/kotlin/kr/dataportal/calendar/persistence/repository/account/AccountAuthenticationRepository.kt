package kr.dataportal.calendar.persistence.repository.account

import kr.dataportal.calendar.persistence.entity.account.AccountAuthenticationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.LongSummaryStatistics

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
interface AccountAuthenticationRepository : JpaRepository<AccountAuthenticationJpaEntity, LongSummaryStatistics>
