package com.wxsoft.triapp.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var onboardingCompleted: Boolean
    var refreshToken: String?
    var token: String?
    var userInfo:String?
}

/**
 * [PreferenceStorage] impl backed by [android.content.SharedPreferences].
 */
class SharedPreferenceStorage @Inject constructor(context: Context) :
    PreferenceStorage {


    companion object {
        const val PREFS_NAME = "triapp"
        const val PREF_ONBOARDING = "pref_onboarding"
        const val PREF_REFRESH_TOKEN = "pref_refresh_token"
        const val PREF_TOKEN = "pref_token"
        const val PREF_USER_INFO = "pref_user_info"
    }
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var onboardingCompleted by BooleanPreference(
        prefs,
        PREF_ONBOARDING,
        false
    )

    override var refreshToken by StringPreference(
        prefs,
        PREF_REFRESH_TOKEN,
        ""
    )

    override var token by StringPreference(
        prefs,
        PREF_TOKEN,
        ""
    )

    override var userInfo by StringPreference(
        prefs,
        PREF_USER_INFO,
        ""
    )
}



class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit().apply { putBoolean(name, value).apply()}
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue)
    }
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit().apply {  putString(name, value).apply()}
    }
}