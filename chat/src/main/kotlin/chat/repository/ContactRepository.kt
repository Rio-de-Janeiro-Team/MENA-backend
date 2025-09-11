package net.thechance.chat.repository

import net.thechance.chat.entity.Contact
import net.thechance.identity.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContactRepository : JpaRepository<Contact, UUID> {
    fun findByOwnerUserAndPhoneNumber(ownerUser: User, phoneNumber: String): Contact?
}