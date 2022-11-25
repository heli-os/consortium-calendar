package kr.dataportal.calendar.account.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import kr.dataportal.calendar.account.AccountAuthenticationType
import kr.dataportal.calendar.account.exception.AlreadyRegisteredEmailException
import kr.dataportal.calendar.account.usecase.RegisterPlainAuthAccountUseCase
import kr.dataportal.calendar.hashSHA512
import kr.dataportal.calendar.persistence.entity.account.AccountAuthenticationJpaEntity
import kr.dataportal.calendar.persistence.entity.account.AccountJpaEntity
import kr.dataportal.calendar.persistence.repository.account.AccountAuthenticationRepository
import kr.dataportal.calendar.persistence.repository.account.AccountRepository
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
internal class RegisterPlainAuthAccountUseCaseTest {

    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var accountAuthenticationRepository: AccountAuthenticationRepository

    private lateinit var sut: RegisterPlainAuthAccountUseCase

    @BeforeEach
    fun init() {
        val now = LocalDateTime.now()
        val accountSlot = slot<AccountJpaEntity>()
        every {
            accountRepository.save(capture(accountSlot))
        } answers {
            accountSlot.captured.apply {
                id = 1L
                createdAt = now
                lastModifiedAt = now
            }
        }

        val accountAuthenticationSlot = slot<AccountAuthenticationJpaEntity>()
        every {
            accountAuthenticationRepository.save(capture(accountAuthenticationSlot))
        } answers {
            accountAuthenticationSlot.captured.apply {
                id = 10L
                createdAt = now
                lastModifiedAt = now
            }
        }

        sut = RegisterPlainAuthAccount(
            accountRepository = accountRepository,
            accountAuthenticationRepository = accountAuthenticationRepository
        )
    }

    private val dummy = RegisterPlainAuthAccountUseCase.Command(
        name = "Heli",
        email = "sun@example.com",
        phoneNumber = "010-1111-2222",
        password = "1q2w3e4r"
    )

    @Test
    fun `이미 등록된 이메일인 경우 AlreadyRegisteredEmailException 가 발생한다`() {
        every { accountRepository.findByEmail(dummy.email) } returns mockk()

        expectThrows<AlreadyRegisteredEmailException> { sut.command(command = dummy) }
    }

    @Test
    fun `비밀번호를 SHA512 HexString 으로 해싱하여 가입한다`() {
        every { accountRepository.findByEmail(any()) } returns null
        val accountAuthenticationSlot = slot<AccountAuthenticationJpaEntity>()
        every {
            accountAuthenticationRepository.save(capture(accountAuthenticationSlot))
        } answers {
            accountAuthenticationSlot.captured.apply {
                id = 10L
                createdAt = LocalDateTime.now()
                lastModifiedAt = LocalDateTime.now()
            }
        }

        val actual = sut.command(command = dummy)
        expectThat(actual) {
            get { id } isEqualTo 1
            get { name } isEqualTo dummy.name
            get { phoneNumber } isEqualTo dummy.phoneNumber
            get { authType } isEqualTo AccountAuthenticationType.PLAIN
        }
        expectThat(accountAuthenticationSlot.captured) {
            get { authText } isEqualTo hashSHA512(dummy.password)
        }
    }
}
