package net.thechance.chat.service

import chat.dto.BaseResponse
import chat.dto.ContactRequest
import net.thechance.chat.mapper.toContact
import net.thechance.chat.repository.ContactRepository
import net.thechance.identity.entity.User
import net.thechance.identity.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ContactService(
    private val userRepository: UserRepository,
    private val contactRepository: ContactRepository,
) {
    fun syncContacts(ownerUser: User, contactRequests: List<ContactRequest>): BaseResponse<Unit> {
        val requestedPhoneNumbers = contactRequests.map(ContactRequest::phoneNumber)

        val existingContacts = contactRepository
            .findAllByOwnerUserAndPhoneNumberIn(ownerUser, requestedPhoneNumbers)
            .associateBy { it.phoneNumber }

        val contactsToSave = contactRequests.map { request ->
            existingContacts[request.phoneNumber]?.copy(name = request.name) ?: request.toContact(ownerUser)
        }

        val contactsToDelete = contactRepository.findAllByOwnerUserAndPhoneNumberNotIn(ownerUser, requestedPhoneNumbers)

        contactRepository.deleteAll(contactsToDelete)
        contactRepository.saveAll(contactsToSave)

        return BaseResponse(status = HttpStatus.OK.value(), success = true, message = "Contacts synced successfully")
    }


    fun getCurrentUser(): User { // todo: delete it when identity feature team provide another one
        val userId = UUID.fromString("7aa6f694-e5ec-4bca-a243-01cedf7f9ce4")

        return userRepository.findById(userId).orElseGet {
            val testUser = User(
                id = userId,
                phoneNumber = "0598202206",
                password = "bilal",
            )
            userRepository.save(testUser)
        }
    }
}