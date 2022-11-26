package kr.dataportal.calendar.config

import kr.dataportal.calendar.notNull
import kr.dataportal.calendar.persistence.config.jpa.BaseEntity
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
abstract class BaseDomain {
    var id: Long? = null
    lateinit var createdAt: LocalDateTime
    lateinit var lastModifiedAt: LocalDateTime
}

val BaseDomain.requiredId: Long
    get() = id.notNull { "Domain(${javaClass.simpleName}) id is null" }

inline fun <reified T : BaseDomain> T.entityData(entity: BaseEntity): T {
    id = entity.requiredId
    createdAt = entity.createdAt
    lastModifiedAt = entity.lastModifiedAt
    return this
}
