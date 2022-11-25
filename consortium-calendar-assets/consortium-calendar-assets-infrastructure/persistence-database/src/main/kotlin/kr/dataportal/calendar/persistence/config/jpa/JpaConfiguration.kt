package kr.dataportal.calendar.persistence.config.jpa

import kr.dataportal.calendar.persistence.Persistence
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackageClasses = [Persistence::class]
)
class JpaConfiguration
