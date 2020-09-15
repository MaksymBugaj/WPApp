package wpa.wp.myapplication.util.language

import android.content.Context

const val PREFERENCE_NAME = "LanguageChanger"
const val PREFERENCE_LANGUAGE = "Language"

class LanguagePreference(context : Context){

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getLanguage() : String{
        return preference.getString(PREFERENCE_LANGUAGE,"en")!!
    }

    fun setLanguage(Language:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_LANGUAGE,Language)
        editor.apply()
    }

}