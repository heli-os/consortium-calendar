package kr.dataportal.calendar.reservation.service

import kr.dataportal.calendar.config.entityData
import kr.dataportal.calendar.organization.exception.NotFoundOrganizationMemberException
import kr.dataportal.calendar.persistence.config.jpa.requiredId
import kr.dataportal.calendar.persistence.entity.reservation.ReservationPageJpaEntity
import kr.dataportal.calendar.persistence.repository.organization.OrganizationMemberRepository
import kr.dataportal.calendar.persistence.repository.reservation.ReservationPageRepository
import kr.dataportal.calendar.reservation.domain.ReservationPage
import kr.dataportal.calendar.reservation.exception.ExistingReservationPageException
import kr.dataportal.calendar.reservation.usecase.CreateReservationPageUseCase
import org.springframework.stereotype.Service

/**
 * @author Heli
 * Created on 2022. 11. 27
 */
@Service
class CreateReservationPage(
    private val organizationMemberRepository: OrganizationMemberRepository,
    private val reservationPageRepository: ReservationPageRepository
) : CreateReservationPageUseCase {

    override fun command(command: CreateReservationPageUseCase.Command): ReservationPage {
        val (title, accountId, organizationId, option) = command

        reservationPageRepository.findByOrganizationJpaEntityId(
            organizationId = organizationId
        )?.let { throw ExistingReservationPageException(organizationId = organizationId) }

        val organizationMemberJpaEntity =
            organizationMemberRepository.findByOrganizationJpaEntityIdAndAccountJpaEntityId(
                organizationId = organizationId,
                accountId = accountId
            ) ?: throw NotFoundOrganizationMemberException(organizationId = organizationId, accountId = accountId)

        val reservationPageJpaEntity = ReservationPageJpaEntity(
            title = title,
            organizationJpaEntity = organizationMemberJpaEntity.organizationJpaEntity,
            option = option
        ).also(reservationPageRepository::save)

        return ReservationPage(
            title = reservationPageJpaEntity.title,
            option = reservationPageJpaEntity.option,
            state = reservationPageJpaEntity.state,
            organizationId = reservationPageJpaEntity.organizationJpaEntity.requiredId
        ).entityData(reservationPageJpaEntity)
    }
}
