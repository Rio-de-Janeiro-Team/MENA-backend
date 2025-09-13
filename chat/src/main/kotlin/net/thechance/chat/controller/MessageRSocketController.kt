package net.thechance.chat.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.thechance.chat.dto.MessageRequest
import net.thechance.chat.dto.MessageResponse
import net.thechance.chat.model.Message
import net.thechance.chat.service.MessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class MessageRSocketController(
    private val messageService: MessageService
) {
    @MessageMapping("messages.add")
    suspend fun addMessage(@Payload request: MessageRequest) {
        val message = Message(
            content = request.content
        )
        messageService.addMessage(message)
    }

    @MessageMapping("messages.stream")
    fun streamMessages(): Flow<MessageResponse> =
        messageService.streamMessages().map {
            MessageResponse(
                id = it.id,
                content = it.content,
                timestamp = it.timestamp.toString()
            )
        }
}