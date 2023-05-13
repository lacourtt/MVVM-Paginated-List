package com.lacourt.githubusers.network

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Para o caso de uma resposta com sucesso
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Para o caso de uma resposta com erro
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()


    /**
     * Para o caso de uma exception antes de obter uma resposta
     *como por exemplo, IOException e UnknownHostException
     */
    data class Exception(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}