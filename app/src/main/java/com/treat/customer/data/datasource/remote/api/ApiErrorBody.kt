package com.treat.customer.data.datasource.remote.api

data class ApiErrorBody(
    var code: Int? = null,
    var status: String? = null,
    var message: String? = null,

    )