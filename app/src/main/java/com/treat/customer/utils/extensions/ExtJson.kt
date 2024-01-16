package com.treat.customer.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.treat.customer.data.datasource.remote.api.ApiErrorBody
import okhttp3.ResponseBody

inline fun <reified T : Any> T?.json() = this?.let { Gson().toJson(this, T::class.java) }

inline fun <reified T : Any> String?.fromJson(): T? = this?.let {
    val type = object : TypeToken<T>() {}.type
    Gson().fromJson(this, type)
}

fun ResponseBody.toApiErrorBody(): ApiErrorBody? {
    val gson = Gson()
    return gson.fromJson(string(), ApiErrorBody::class.java)
}
//
//fun ResponseBody.toApiError(): ApiError? {
//    val gson = Gson()
//    return gson.fromJson(string(), ApiError::class.java)
//}

