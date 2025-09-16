package net.thechance.chat.dto

import java.util.UUID

 data class MessageRequest(
    val content: String
)

 data class MessageResponse(
    val id: UUID,
    val content: String,
    val timestamp: String
)