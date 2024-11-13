package com.example.android_2425_gent2.data.repository

/*
* APIResource of a type T holds the object and an optional message
* It can be
*   APIResource.Loading()
*   APIResource.Success(...response...)
*   APIResource.Error(...)
*
* The resource is used by the Repo to encapsulate api responses
* */
sealed class APIResource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : APIResource<T>(data)
    class Success<T>(data: T?) : APIResource<T>(data)
    class Error<T>(message: String, data: T? = null) : APIResource<T>(data, message)
}