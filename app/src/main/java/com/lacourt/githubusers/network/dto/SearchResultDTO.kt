package com.lacourt.githubusers.network.dto

data class SearchResultDTO(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserListedDTO>
)