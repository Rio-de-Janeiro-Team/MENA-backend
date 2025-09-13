package net.thechance.chat.service

import net.thechance.chat.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MessageService {
    private val messages = mutableListOf<Message>()
    private val messageFlow = MutableSharedFlow<Message>(replay = 100)

    suspend fun addMessage(message: Message) {
        messages.add(message)
        messageFlow.emit(message)
    }

    fun streamMessages(): Flow<Message> =
        messageFlow.asSharedFlow()
}

