package com.treat.customer.domain.entities

import com.treat.customer.R


data class Language(val icon: Int, val name: Int)
val languages = listOf<Language>(
    Language(R.drawable.united_kingdom, R.string.english),
    Language(R.drawable.saudi_arabia, R.string.arabic),)