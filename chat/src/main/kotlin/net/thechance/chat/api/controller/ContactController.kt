package net.thechance.chat.api.controller

import net.thechance.chat.api.dto.ContactRequest
import net.thechance.chat.api.dto.ContactResponse
import net.thechance.chat.api.dto.PagedResponse
import net.thechance.chat.service.ContactService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/chat/contacts")
class ContactController(
    private val contactService: ContactService
) {

    @PostMapping("/sync")
    fun syncContacts(
        @RequestBody contacts: List<ContactRequest>
    ): ResponseEntity<String> {
        val currentUser = contactService.getCurrentUser().id
        contactService.syncContacts(currentUser, contacts)
        return ResponseEntity.ok("Contacts synced successfully")
    }

    @GetMapping
    fun getPagedContact(pageable: Pageable): ResponseEntity<PagedResponse<ContactResponse>> {
        val userId = contactService.getCurrentUser().id
        val response = contactService.getPagedContacts(userId = userId, pageable = pageable)
        return ResponseEntity.ok(response)
    }
}