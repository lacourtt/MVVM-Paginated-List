package com.lacourt.githubusers.model

data class UserListed(
    val id: Int,
    val login: String,
    val avatar_url: String,
    var lastId: Int = 0
)