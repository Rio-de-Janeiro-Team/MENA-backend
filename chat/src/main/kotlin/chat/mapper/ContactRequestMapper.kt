package net.thechance.chat.mapper

import chat.dto.ContactRequest
import net.thechance.chat.entity.Contact
import net.thechance.identity.entity.User

fun ContactRequest.toContact(ownerUser: User): Contact{
    return Contact(
        name = name,
        phoneNumber = phoneNumber,
        ownerUser = ownerUser
    )
}