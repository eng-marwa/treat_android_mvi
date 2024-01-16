package com.treat.customer.domain.entities

import com.treat.customer.R

data class Menu(val icon: Int, val name: Int)

val menuItems = listOf<Menu>(
    Menu(R.drawable.bx_user, R.string.my_profile),
    Menu(R.drawable.bx_coin_stack, R.string.my_points),
    Menu(R.drawable.bx_wallet_alt, R.string.my_wallet),
    Menu(R.drawable.bx_map_pin, R.string.my_change_location),
    Menu(R.drawable.bx_locale, R.string.change_language),
    Menu(R.drawable.info, R.string.fqa),
    Menu(R.drawable.bx_terms, R.string.terms),
    Menu(R.drawable.bx_phone_call, R.string.contact_us),
    Menu(R.drawable.bx_share_alt, R.string.share),
    Menu(R.drawable.log_out, R.string.logout),
    Menu(R.drawable.user_remove, R.string.disable_account),
)
