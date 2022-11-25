package kr.dataportal.calendar.persistence.entity.account

import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Entity
@Table(name = "account")
class AccountJpaEntity(
    var name: String,
    var email: String,
    var phoneNumber: String
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    var state: State = State.ACTIVATE

    enum class State {
        ACTIVATE, DEACTIVATE
    }
}
