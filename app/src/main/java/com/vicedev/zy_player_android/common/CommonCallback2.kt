package com.vicedev.zy_player_android.common

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 15:09
 */
interface CommonCallback2<T> {
    fun onResult(success: Boolean, t: T)
}