package com.treat.customer.data.datasource.remote.api

import com.treat.customer.base.BaseException

open class NetworkResource<T>(
    val status: NetworkStatus? = NetworkStatus.Idle,
    val payload: NetworkPayload<T>? = null,
    val error: BaseException? = null
) {
    open class Success<T>(data: T) : NetworkResource<T>(
        NetworkStatus.Success,
        NetworkPayload.create(data)
    )

    open class Failure(error: BaseException) : NetworkResource<BaseException>(
        NetworkStatus.Failure,
        error = error
    )

    open class Loading() : NetworkResource<Boolean>(NetworkStatus.Loading)
    open class Ready<T>() : NetworkResource<T>()
}