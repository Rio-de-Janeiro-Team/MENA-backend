package net.thechance.chat.controller

import net.thechance.chat.dto.BaseResponse
import net.thechance.chat.dto.ContactRequest
import net.thechance.chat.dto.ContactResponse
import net.thechance.chat.dto.PagedResponse
import net.thechance.chat.service.ContactService
import net.thechance.identity.security.JwtFilter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import java.util.UUID

@RestController
@RequestMapping("/contacts")
class ContactController(
    private val contactService: ContactService
) {

    @PostMapping("/sync")
    fun syncContacts(
        @RequestBody contacts: List<ContactRequest>
    ): BaseResponse<Unit> {
        val currentUser = contactService.getCurrentUser()
        return contactService.syncContacts(currentUser, contacts)
    }

    @GetMapping
    fun getPagedContact(
        @RequestParam pageNumber: Int = 1,
        @RequestParam pageSize: Int = 20,
    ): ResponseEntity<BaseResponse<PagedResponse<ContactResponse>>> {
        val userId = UUID.fromString("7aa6f694-e5ec-4bca-a243-01cedf7f9ce4")

        val data = contactService.getPagedContacts(userId = userId, pageNumber = pageNumber, pageSize = pageSize)
        return ResponseEntity.ok(
            BaseResponse(
                body = data,
                status = HttpStatus.OK.value(),
                message = "fetch data successfully",
                success = true,
            )
        )
    }
}