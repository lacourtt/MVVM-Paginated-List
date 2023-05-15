package com.lacourt.githubusers.viewmodel

import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.network.RequestError
import java.io.IOException

object TestUtil {
    val apiError = NetworkResponse.ApiError(RequestError(403, "Forbidden"), 403)
    val networkError = NetworkResponse.NetworkError(IOException("unable to resolve host"))
    val unknownError = NetworkResponse.UnknownError(Exception("no internet connection"))
}