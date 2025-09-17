package net.thechance.chat.api.dto

data class PagedResponse<T>(
    val data: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int
)
