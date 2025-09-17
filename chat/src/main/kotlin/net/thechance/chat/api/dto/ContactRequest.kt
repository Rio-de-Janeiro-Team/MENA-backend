package net.thechance.chat.api.dto

import net.thechance.chat.entity.Contact
import java.util.UUID

data class ContactRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)

fun ContactRequest.toContact(userId: UUID): Contact {
    return Contact(
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        userId = userId
    )
}