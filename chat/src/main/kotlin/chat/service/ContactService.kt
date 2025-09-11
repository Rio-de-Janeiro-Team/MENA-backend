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
        return try {
            val contactsToSave = contactRequests.map { request ->
                val existingContact = contactRepository.findByOwnerUserAndPhoneNumber(ownerUser, request.phoneNumber)
                existingContact?.copy(name = request.name, phoneNumber = request.phoneNumber) ?: request.toContact(ownerUser)
            }

            contactRepository.saveAll(contactsToSave)

            BaseResponse(status = 200, success = true, message = "Contacts synced successfully")
        } catch (exception: Exception) {
            BaseResponse(status = 500, success = false, message = "Failed to sync contacts: ${exception.message}")
        }
    }

    fun getCurrentUser(): User { // todo: delete it when identity feature team provide another one
        val userId = UUID.fromString("7aa6f694-e5ec-4bca-a243-01cedf7f9ce4")

        return userRepository.findById(userId).orElseGet {
            val testUser = User(
                id = userId,
                phoneNumber = "0598202206",
                password = "bilal",
                imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.workitdaily.com%2Fpersonal-branding-statement&psig=AOvVaw2fUXAADnAHQ_dJ5ofTc26Q&ust=1757664600168000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCIDGloSh0I8DFQAAAAAdAAAAABAE"
            )
            userRepository.save(testUser)
        }
    }
}