package com.lacourt.githubusers.network.dto

data class UserRepositoryDTO(
    val id: Long,
    val nodeId: String,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val owner: OwnerDTO,
    val htmlUrl: String,
    val description: String,
    val webCommitSignoffRequired: Boolean,
    val topics: List<String>,
    val visibility: String,
    val forks: Int,
    val openIssues: Int,
    val watchers: Int,
    val defaultBranch: String
)