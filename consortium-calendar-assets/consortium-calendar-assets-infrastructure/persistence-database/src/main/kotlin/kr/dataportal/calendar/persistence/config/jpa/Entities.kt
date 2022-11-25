package kr.dataportal.calendar.persistence.config.jpa

import kr.dataportal.calendar.notNull
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(updatable = false)
    lateinit var createdAt: LocalDateTime

    lateinit var lastModifiedAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        lastModifiedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        lastModifiedAt = LocalDateTime.now()
    }
}

val BaseEntity.requiredId: Long
    get() = id.notNull { "Entity(${javaClass.simpleName}) id is null" }
