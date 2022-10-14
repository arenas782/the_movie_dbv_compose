package com.example.themoviedb.utils

import android.content.Context
import android.content.SharedPreferences


import androidx.core.content.edit
import com.example.themoviedb.BaseApp
import java.util.*


class PreferencesManager {

    val TAG = PreferencesManager::class.java.simpleName

    var myPrefs: SharedPreferences? = null

    companion object {
        var pref: PreferencesManager? = null
        fun getInstance(): PreferencesManager {
            if (pref == null) {
                pref = PreferencesManager(BaseApp.appContext)
            }

            return pref!!
        }
    }

    init {
        val context = BaseApp.appContext
        myPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }

    constructor(context: Context) {
        myPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun putString(key: String, value: String?): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putString(key, value ?: "")
        return editor?.commit()
    }

    fun clearAll(){
        myPrefs?.edit()?.clear()?.apply()
    }

    fun putStringSet(key: String, values: List<String>): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putStringSet(key, HashSet(values))
        return editor?.commit()
    }

    fun getStringSet(key: String): MutableList<String> {
        val list = mutableListOf<String>()
        myPrefs?.let {
            val vals = it.getStringSet(key, null)
            if (vals != null)
                list.addAll(vals)
        }
        return list
    }

    fun getString(key: String, default_value: String?): String? {
        return myPrefs?.getString(key, default_value)
    }

    fun putInt(key: String, value: Int): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putInt(key, value)
        return editor?.commit()
    }

    fun getInt(key: String, default_value: Int): Int {
        return try {
            Integer.parseInt(myPrefs!!.getString(key, default_value.toString() + "").toString())
        } catch (e: Exception) {
            myPrefs!!.getInt(key, default_value)
        }

    }

    fun putBoolean(key: String, value: Boolean?) {
        val editor = myPrefs?.edit()
        editor?.putBoolean(key, value ?: false)
        editor?.apply()
    }


    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return myPrefs!!.getBoolean(key, defValue)
    }

    fun setDate(key: String, value: Date): Boolean? {
        val time = value.time
        val editor = myPrefs?.edit()
        editor?.putLong(key, time)
        return editor?.commit()
    }


    fun getDate(key: String): Date? {
        return try {
            val value = myPrefs!!.getLong(key, -1)
            if (!value.equals(-1)) {
                Date(value)
            } else
                null
        } catch (e: Exception) {
            null
        }
    }

    fun putLong(key: String, value: Long) {
        myPrefs?.edit { putLong(key, value) }
    }
}