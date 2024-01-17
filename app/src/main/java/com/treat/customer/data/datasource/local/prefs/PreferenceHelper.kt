package com.treat.customer.data.datasource.local.prefs

import android.content.SharedPreferences
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.utils.extensions.fromJson
import com.treat.customer.utils.extensions.json


class PreferenceHelper(private val sharedPreferences: SharedPreferences) {


    //region var
    private val USER_DATA = "user_data"
    private val USER_TOKEN = "user_token"
    private val LOCATION = "location"
    private val IS_LOGGED_IN = "login_status"
    private val LANGUAGE = "language"
    private val GENDER = "gender"
    private val FCM = "fcm"

    //endregion


    var language: String
        get() = sharedPreferences[LANGUAGE]
        set(value) {
            sharedPreferences[LANGUAGE] = value
        }
    var gender: GenderData?
        get() = sharedPreferences.getString(GENDER, null).fromJson()
        set(value) {
            sharedPreferences[GENDER] = value.json()
        }

    var userData: ProfileResponse?
        get() = sharedPreferences.getString(USER_DATA, null).fromJson()
        set(value) {
            sharedPreferences[USER_DATA] = value.json()
        }
    var userToken: String?
        get() = sharedPreferences[USER_TOKEN]
        set(value) {
            sharedPreferences[USER_TOKEN] = value
        }
    var location: String?
        get() = sharedPreferences[LOCATION]
        set(value) {
            sharedPreferences[LOCATION] = value
        }

    var fcm: String?
        get() = sharedPreferences[FCM]
        set(value) {
            sharedPreferences[FCM] = value
        }

//    var deviceToken: String?
//        get() = sharedPreferences[DEVICE_TOKEN]
//        set(value) {
//            sharedPreferences[DEVICE_TOKEN] = value
//        }


    var loggedIn: Boolean
        get() = sharedPreferences[IS_LOGGED_IN]
        set(value) {
            sharedPreferences[IS_LOGGED_IN] = value
        }


}