package by.htp.first.homework6_1.function

import android.view.View

fun View.setVisibileOrNot(isVisible: Boolean) {
    this.visibility = if(isVisible) View.INVISIBLE else View.VISIBLE
}