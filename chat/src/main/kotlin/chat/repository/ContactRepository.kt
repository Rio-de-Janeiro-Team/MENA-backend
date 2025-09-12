package net.thechance.chat.repository

import net.thechance.chat.dto.ContactResponse
import net.thechance.chat.entity.Contact
import net.thechance.identity.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface ContactRepository : JpaRepository<Contact, UUID> {
    fun findAllByOwnerUserAndPhoneNumberIn(ownerUser: User, phoneNumbers: List<String>): List<Contact>
    fun findAllByOwnerUserAndPhoneNumberNotIn(ownerUser: User, phoneNumbers: Collection<String>): List<Contact>

    @Query(
        "SELECT new net.thechance.chat.dto.ContactResponse(" +
                "c.id, c.name, c.phoneNumber, " +
                "CASE WHEN u IS NOT NULL THEN 'https://picsum.photos/200' ELSE null END, " +
                "CASE WHEN u IS NOT NULL THEN true ELSE false END) " +
                "FROM Contact c " +
                "LEFT JOIN User u ON u.phoneNumber = c.phoneNumber " +
                "WHERE c.ownerUser.id = :ownerUserId"
    ) //TODO: will be changed later with another table
    fun findAllContactResponsesByOwnerUserId(
        @Param("ownerUserId") ownerUserId: UUID,
        pageable: Pageable?
    ): Page<ContactResponse>

}
