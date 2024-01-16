package com.treat.customer.utils.extensions

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun AppCompatActivity.replaceFragmentCustomAnim(
    fragment: Fragment,
    @IdRes id: Int,
    addToBackStack: Boolean = false,
    @AnimRes animIn: Int? = null,
    @AnimRes animOut: Int? = null
) {

    val trans = supportFragmentManager.beginTransaction()

    if (animIn != null && animOut != null)
        trans.setCustomAnimations(animIn, animOut)

    trans.replace(id, fragment)

    if (addToBackStack)
        trans.addToBackStack("")

    trans.commit()
}



