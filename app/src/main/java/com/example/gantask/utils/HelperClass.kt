package com.example.gantask.utils

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gantask.rest.data.BBCharactersData

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let { addToBackStack(fragment.javaClass.name) }
    }
}

fun AppCompatActivity.addAndReplaceFragment(
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null
) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let { addToBackStack(fragment.javaClass.name) }
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun filterList(
    searchInformation: String,
    season: Int,
    list: List<BBCharactersData>
): List<BBCharactersData> {
    val sortedList = ArrayList<BBCharactersData>()

    if (season != 0) {
        //Add all characters that have appearance in the selected season
        for (character in list) {
            if (!character.appearance.isNullOrEmpty() && character.appearance.contains(season))
                sortedList.add(character)
        }
    } else {
        //Add all characters that has the char sequence in their names
        for (character in list) {
            if (character.name.toLowerCase().trim()
                    .contains(searchInformation.toLowerCase().trim())
            ) {
                sortedList.add(character)
            }
        }
    }

    return sortedList
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

//Hide keyboard from Activity
fun hideKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(R.id.content)
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


