package com.vicedev.zy_player_android.common

import com.blankj.utilcode.util.ThreadUtils

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/18 19:08
 * @desc Task，对回调做catch，防止崩溃
 */

class Task<T>(private val doInBackground: (() -> (T?))?, private val callback: ((T?) -> Unit)?) :
    ThreadUtils.Task<T>() {
    override fun doInBackground(): T? {
        try {
            return doInBackground?.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onSuccess(result: T) {
        try {
            callback?.invoke(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFail(t: Throwable?) {
        try {
            callback?.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCancel() {
        try {
            callback?.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}