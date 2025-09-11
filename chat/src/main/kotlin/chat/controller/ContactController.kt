package net.thechance.chat.controller

import chat.dto.BaseResponse
import chat.dto.ContactRequest
import net.thechance.chat.service.ContactService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contact")
class ContactController(private val contactService: ContactService) {

    @PostMapping("/sync")
    fun syncContacts(
        @RequestBody contacts: List<ContactRequest>
    ): BaseResponse<Unit> {
        val currentUser = contactService.getCurrentUser()
        return contactService.syncContacts(currentUser, contacts)
    }
}