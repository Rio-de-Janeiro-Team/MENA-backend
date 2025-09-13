package net.thechance.chat.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig {
    @Bean
    fun messageService() = MessageService()
}