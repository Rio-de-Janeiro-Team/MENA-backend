package net.thechance.chat.repository

import net.thechance.chat.entity.Contact
import net.thechance.identity.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ContactRepository : JpaRepository<Contact, UUID> {
    fun findAllByOwnerUserAndPhoneNumberIn(ownerUser: User, phoneNumbers: List<String>): List<Contact>
    fun findAllByOwnerUserAndPhoneNumberNotIn(ownerUser: User, phoneNumbers: Collection<String>): List<Contact>
    fun findAllByOwnerUserId(ownerUserId: UUID, pageable: Pageable): Page<Contact>
}
