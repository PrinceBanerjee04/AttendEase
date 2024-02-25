package com.bp.attendease.db.repos

sealed class Response<T>(val errorMassage: String? = null){
    class Success<T>(): Response<T>()
    class Failure<T>(errorMassage: String): Response<T>(errorMassage = errorMassage)
}
