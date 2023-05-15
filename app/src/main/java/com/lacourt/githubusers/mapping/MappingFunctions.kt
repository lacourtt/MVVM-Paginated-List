package com.lacourt.githubusers.mapping

import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.dto.UserDetailsDTO
import com.lacourt.githubusers.network.dto.UserListedDTO
import com.lacourt.githubusers.model.UserDetails

fun List<UserListedDTO>.asDomainModel() =
    map {
        UserListed(
            id = it.id,
            login = it.login,
            avatar_url = it.avatar_url
        )
    }

fun UserDetailsDTO.asDomainModel() =
    UserDetails(
        avatar_url = avatar_url ?: "",
        name = name ?: "---",
        company = company ?: "",
        location = location ?: "",
        public_repos = public_repos,
        followers = followers
    )
