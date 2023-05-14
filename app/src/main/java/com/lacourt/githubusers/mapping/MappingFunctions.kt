package com.lacourt.githubusers.mapping

import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.dto.UserListedDTO

fun List<UserListedDTO>.asDomainModel() =
    map {
        UserListed(
            login = it.login,
            avatar_url = it.avatar_url
        )
    }

fun UserListedDTO.asDomainModel() =
    UserListed(
        login = login,
        avatar_url = avatar_url
    )
