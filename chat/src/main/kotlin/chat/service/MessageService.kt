package net.thechance.chat.service

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import net.thechance.chat.entity.Message
import org.springframework.stereotype.Service

@Service
class MessageService {
    private val messages = mutableListOf<Message>()
    private val messageFlow = MutableSharedFlow<Message>(replay = 100)

    suspend fun addMessage(message: Message) {
        messages.add(message)
        messageFlow.emit(message)
    }

    fun streamMessages() = messageFlow.asSharedFlow()
}

