package net.thechance.chat.dto

import java.util.UUID

data class ContactResponse(
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val imageUrl: String? = null,
    val isMenaMember: Boolean
)
