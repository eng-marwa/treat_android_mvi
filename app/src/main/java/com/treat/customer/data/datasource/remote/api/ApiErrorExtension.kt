package com.treat.customer.data.datasource.remote.api

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

fun ResponseBody.toApiErrorBody(): ApiErrorBody? {
    val errorBody = ApiErrorBody()
    try {
        val obj = JSONObject(this.string())
        errorBody.code = obj.getInt("code")
        errorBody.message = obj.getString("message")
        errorBody.status = obj.getString("status")
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return errorBody
}