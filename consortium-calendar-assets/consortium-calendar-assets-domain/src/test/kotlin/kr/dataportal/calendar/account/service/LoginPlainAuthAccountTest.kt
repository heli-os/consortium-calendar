package kr.dataportal.calendar.account.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.account.exception.FailedLoginException
import kr.dataportal.calendar.account.usecase.LoginPlainAuthAccountUseCase
import kr.dataportal.calendar.hashSHA512
import kr.dataportal.calendar.persistence.entity.account.AccountAuthenticationJpaEntity
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountAuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
@ExtendWith(MockKExtension::class)
internal class LoginPlainAuthAccountTest {

    @MockK
    private lateinit var accountAuthenticationRepository: AccountAuthenticationRepository

    private lateinit var sut: LoginPlainAuthAccountUseCase

    @BeforeEach
    fun init() {
        sut = LoginPlainAuthAccount(
            accountAuthenticationRepository = accountAuthenticationRepository
        )
    }

    private val dummy = LoginPlainAuthAccountUseCase.Query(
        email = "sun@example.com",
        password = "1q2w3e4r"
    )

    private val accountAuthenticationJpaEntity = AccountAuthenticationJpaEntity(
        authType = AccountAuthenticationType.PLAIN,
        authText = "password",
        accountJpaEntity = AccountJpaEntity(
            name = "Heli",
            email = "sun@example.com",
            phoneNumber = "010-1111-2222"
        ).apply { id = 2 }
    ).apply {
        id = 1
        createdAt = LocalDateTime.now()
        lastModifiedAt = LocalDateTime.now()
    }

    @Test
    fun `이메일 주소와 비밀번호가 일치하지 않으면 FailedLoginException 예외가 발생한다`() {
        every {
            accountAuthenticationRepository.fetchByEmailAndAuthTypeAndAuthText(
                email = dummy.email,
                authType = AccountAuthenticationType.PLAIN,
                any()
            )
        } returns null
        expectThrows<FailedLoginException> { sut.query(query = dummy) }
    }

    @Test
    fun `이메일 주소와 비밀번호로 로그인할 수 있다`() {
        val authTextSlot = slot<String>()
        every {
            accountAuthenticationRepository.fetchByEmailAndAuthTypeAndAuthText(
                email = dummy.email,
                authType = AccountAuthenticationType.PLAIN,
                capture(authTextSlot)
            )
        } returns accountAuthenticationJpaEntity

        val actual = sut.query(query = dummy)
        expectThat(actual) {
            get { id } isEqualTo 1
            get { email } isEqualTo accountAuthenticationJpaEntity.accountJpaEntity.email
            get { name } isEqualTo accountAuthenticationJpaEntity.accountJpaEntity.name
            get { phoneNumber } isEqualTo accountAuthenticationJpaEntity.accountJpaEntity.phoneNumber
            get { authType } isEqualTo AccountAuthenticationType.PLAIN
        }
        expectThat(authTextSlot.captured) {
            isEqualTo(hashSHA512(dummy.password))
        }
        verify(exactly = 1) {
            accountAuthenticationRepository.fetchByEmailAndAuthTypeAndAuthText(
                email = dummy.email,
                authType = AccountAuthenticationType.PLAIN,
                authText = hashSHA512(dummy.password)
            )
        }
    }
}
