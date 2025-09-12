package chat.controller

import chat.dto.BaseResponse
import chat.dto.ContactRequest
import chat.dto.baseResponse
import net.thechance.chat.dto.ContactResponse
import net.thechance.chat.dto.PagedResponse
import net.thechance.chat.service.ContactService
import net.thechance.identity.security.JwtFilter
import org.springframework.http.ResponseEntity
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
    @GetMapping
    fun getPagedContact(
        @RequestParam pageNumber: Int = 1,
        @RequestParam pageSize: Int = 20,
    ): ResponseEntity<BaseResponse<PagedResponse<ContactResponse>>> {
        val userId = JwtFilter.getUserId()

        val data = contactService.getPagedContacts(userId = userId, pageNumber = pageNumber, pageSize = pageSize)
        return baseResponse(
            data = data,
            message = "fetch data successfully",
            success = true,
        )
    }
}