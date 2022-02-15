package by.htp.first.myproject.function

import android.view.View

fun View.setVisibileOrNot(isVisible: Boolean) {
    this.visibility = if(isVisible) View.INVISIBLE else View.VISIBLE
}