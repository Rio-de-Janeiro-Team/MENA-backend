package net.thechance.chat.service

import chat.dto.BaseResponse
import chat.dto.ContactRequest
import net.thechance.chat.mapper.toContact
import net.thechance.chat.repository.ContactRepository
import net.thechance.identity.entity.User
import net.thechance.identity.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactService(
    private val userRepository: UserRepository,
    private val contactRepository: ContactRepository,
) {
    fun syncContacts(
        ownerUser: User,
        contactRequests: List<ContactRequest>
    ): BaseResponse<Unit> {

        val contactsToSave = contactRequests.map { request ->
            val existingContact = contactRepository.findByOwnerUserAndPhoneNumber(ownerUser, request.phoneNumber)
            existingContact?.copy(name = request.name, phoneNumber = request.phoneNumber) ?: request.toContact(ownerUser)
        }

        contactRepository.saveAll(contactsToSave)

        return BaseResponse(status = 200, success = true, message = "Contacts synced successfully")
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