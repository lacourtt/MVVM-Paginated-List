package com.lacourt.githubusers.model

data class UserListed(
    val login: String,
    val avatar_url: String
) {
    var lastId = 0
}