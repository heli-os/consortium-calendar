package kr.dataportal.calendar.account.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kr.dataportal.calendar.account.exception.NotFoundAccountException
import kr.dataportal.calendar.account.usecase.QueryAccountByIdUseCase
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@ExtendWith(MockKExtension::class)
internal class QueryAccountByIdTest {
    @MockK
    private lateinit var accountRepository: AccountRepository

    private lateinit var sut: QueryAccountByIdUseCase

    @BeforeEach
    fun init() {
        sut = QueryAccountById(
            accountRepository = accountRepository
        )
    }

    private val dummy = QueryAccountByIdUseCase.Query(
        accountId = 1
    )

    private val accountJpaEntity = AccountJpaEntity(
        name = "Heli",
        email = "sun@example.com",
        phoneNumber = "010-1111-2222"
    ).apply {
        id = 1
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    @Test
    fun `존재하지 않는 AccountId 인 경우 NotFoundAccountException 예외가 발생한다`() {
        every { accountRepository.findByIdOrNull(id = 1) } returns null
        expectThrows<NotFoundAccountException> { sut.query(query = dummy) }
    }

    @Test
    fun `AccountId로 조회 성공`() {
        every { accountRepository.findByIdOrNull(id = 1) } returns accountJpaEntity

        val actual = sut.query(query = dummy)
        expectThat(actual) {
            get { id } isEqualTo 1
            get { email } isEqualTo accountJpaEntity.email
            get { name } isEqualTo accountJpaEntity.name
            get { phoneNumber } isEqualTo accountJpaEntity.phoneNumber
        }
        verify(exactly = 1) { accountRepository.findByIdOrNull(id = 1) }
    }
}
