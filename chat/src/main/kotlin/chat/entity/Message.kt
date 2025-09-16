package net.thechance.chat.entity

import java.time.Instant
import java.util.UUID

data class Message(
    val id: UUID = UUID.randomUUID(),
    val content: String,
    val timestamp: Instant = Instant.now()
)