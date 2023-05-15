package com.lacourt.githubusers.di

import com.lacourt.githubusers.paging.UserListPageAdapter
import org.koin.dsl.module

val activityModule = module {
    factory { UserListPageAdapter() }
}
