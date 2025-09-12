package chat.mapper

import net.thechance.chat.dto.ContactResponse
import net.thechance.chat.entity.Contact

fun Contact.toContactResponse(imageUrl: String?, isMenaUser: Boolean): ContactResponse{
    return ContactResponse(
        id = this.id.toString(),
        name = name,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
        isMenaMember = isMenaUser
    )
}