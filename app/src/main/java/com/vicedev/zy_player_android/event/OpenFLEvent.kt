package com.vicedev.zy_player_android.event

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/19 18:42
 * @desc 开启福利的事件
 */

class OpenFLEvent(val open: Boolean) {
    override fun equals(other: Any?): Boolean {
        return other is OpenFLEvent
    }
}