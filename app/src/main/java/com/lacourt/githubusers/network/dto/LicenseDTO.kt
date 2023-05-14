package com.lacourt.githubusers.network.dto

data class LicenseDTO(
    val key: String,
    val name: String,
    val spdxId: String,
    val url: String,
    val nodeId: String
)