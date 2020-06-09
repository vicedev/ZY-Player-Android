package com.vicedev.zy_player_android.common

import android.view.View
import androidx.core.view.isVisible

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 18:14
 */

fun View.visible() {
    if (!isVisible) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (isVisible) {
        visibility = View.INVISIBLE
    }
}

fun View.gone() {
    if (isVisible) {
        visibility = View.GONE
    }
}