package net.thechance.chat.service

import net.thechance.chat.api.dto.ContactRequest
import net.thechance.chat.api.dto.ContactResponse
import net.thechance.chat.api.dto.PagedResponse
import net.thechance.chat.api.dto.toContact
import net.thechance.chat.repository.ContactRepository
import net.thechance.identity.entity.User
import net.thechance.identity.repository.UserRepository
import net.thechance.mena.mapper.toPagedResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ContactService(
    private val userRepository: UserRepository,
    private val contactRepository: ContactRepository,
) {
    fun syncContacts(userId: UUID, contactRequests: List<ContactRequest>) {
        val requestedPhoneNumbers = contactRequests.map { it.phoneNumber }

        val existingContactsMap = contactRepository
            .findAllByUserIdAndPhoneNumberIn(userId, requestedPhoneNumbers)
            .associateBy { it.phoneNumber }

        val contactsToSave = contactRequests.map { request ->
            existingContactsMap[request.phoneNumber]?.copy(
                firstName = request.firstName,
                lastName = request.lastName
            ) ?: request.toContact(userId)
        }

        contactRepository.deleteAllByUserIdAndPhoneNumberNotIn(userId, requestedPhoneNumbers)
        contactRepository.saveAll(contactsToSave)
    }


    fun getCurrentUser(): User { // todo: delete it when identity feature team provide another one
        val userId = UUID.fromString("49a905c3-15bb-4eaa-a241-252c5ce0be08")

        return userRepository.findById(userId).orElseGet {
            val testUser = User(
                id = userId,
                phoneNumber = "0598202206",
                password = "bilal",
            )
            userRepository.save(testUser)
        }
    }

    fun getPagedContacts(userId: UUID, pageable: Pageable): PagedResponse<ContactResponse> {
        val pagedData = if (pageable.pageNumber <= 0 || pageable.pageSize <= 0) {
            contactRepository.findAllContactResponsesByOwnerUserId(userId, Pageable.unpaged())
        } else {
            contactRepository.findAllContactResponsesByOwnerUserId(
                userId,
                PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.by("firstName").ascending())
            )
        }
        return pagedData.toPagedResponse()
    }
}