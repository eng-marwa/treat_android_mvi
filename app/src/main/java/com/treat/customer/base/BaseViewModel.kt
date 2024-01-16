package com.treat.customer.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    open class ViewState<T>(open val data: T? = null) {
        class Empty<T> : ViewState<T>()
        class Loaded<T>(data: T?) : ViewState<T>(data)
        class Failed<T>(val throwable: BaseException? = null) : ViewState<T>()
    }
}