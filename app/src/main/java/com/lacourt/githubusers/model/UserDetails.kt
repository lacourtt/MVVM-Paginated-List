package com.lacourt.githubusers.model

data class UserDetails(
    val avatar_url: String?,
    val name: String?,
    val company: String?,
    val location: String?,
    val public_repos: Int?,
    val followers: Int?,
)