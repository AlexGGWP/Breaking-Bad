package com.example.gantask.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.gantask.R
import com.example.gantask.utils.addAndReplaceFragment
import com.example.gantask.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_search_layout.*
import kotlinx.android.synthetic.main.custom_toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_tV.clearFocus()

        addAndReplaceFragment(
            MainFragment(),
            R.id.fragment_container
        )

        back_arrow_button.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }
        }

        search_icon.setOnClickListener {
            if (expandable_view.isExpanded) {
                closeExpandableView()
            } else {
                search_tV.requestFocus()
                expandable_view.expand()
                search_icon.setImageResource(R.drawable.ic_close_white)
            }
        }
    }

    //Making sure that the searchview remains hidden if clicked outside of it
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v: View? = currentFocus
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(
                            ev.rawX.toInt(),
                            ev.rawY.toInt()
                        ) && expandable_view.isExpanded
                    ) {
                        closeExpandableView()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun closeExpandableView() {
        search_tV.clearFocus()
        search_icon.setImageResource(R.drawable.ic_search_white)
        expandable_view.toggle()
        //Hide the keyboard when the search view is hidden
        hideKeyboard(this)
    }
}