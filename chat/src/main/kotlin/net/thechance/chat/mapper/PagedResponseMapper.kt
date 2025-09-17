package net.thechance.mena.mapper

import net.thechance.chat.api.dto.PagedResponse
import org.springframework.data.domain.Page

fun <T> Page<T>.toPagedResponse(): PagedResponse<T> {
    return PagedResponse(
        data = content,
        pageNumber = number + 1,
        pageSize = size,
        totalItems = totalElements,
        totalPages = totalPages
    )
}