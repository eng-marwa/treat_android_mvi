package com.treat.customer.domain.entities

data class MultipleChoiceData<T>( val data:T?,
                          var isSelected: Boolean = false) {
}