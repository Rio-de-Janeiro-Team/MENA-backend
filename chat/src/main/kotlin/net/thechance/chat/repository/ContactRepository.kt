package net.thechance.chat.repository

import net.thechance.chat.api.dto.ContactResponse
import net.thechance.chat.entity.Contact
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface ContactRepository : JpaRepository<Contact, UUID> {
    fun findAllByUserIdAndPhoneNumberIn(userId: UUID, phoneNumbers: List<String>): List<Contact>
    fun deleteAllByUserIdAndPhoneNumberNotIn(userId: UUID, requestedPhoneNumbers: List<String>)

    @Query(
        "SELECT new net.thechance.chat.api.dto.ContactResponse(" +
                "c.id, c.firstName, c.lastName, c.phoneNumber, " +
                "CASE WHEN u IS NOT NULL THEN 'https://picsum.photos/200' ELSE null END, " +
                "CASE WHEN u IS NOT NULL THEN true ELSE false END) " +
                "FROM Contact c " +
                "LEFT JOIN User u ON u.phoneNumber = c.phoneNumber " +
                "WHERE c.userId = :userId"
    ) //TODO: will be changed later with another table
    fun findAllContactResponsesByOwnerUserId(
        @Param("userId") userId: UUID,
        pageable: Pageable?
    ): Page<ContactResponse>

}
