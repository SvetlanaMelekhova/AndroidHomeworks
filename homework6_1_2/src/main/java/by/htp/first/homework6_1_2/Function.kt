package by.htp.first.homework6_1_2

import android.view.View

fun View.setVisibileOrNot(isVisible: Boolean) {
    this.visibility = if(isVisible) View.INVISIBLE else View.VISIBLE
}