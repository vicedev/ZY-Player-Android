package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.util.AttributeSet
import cn.jzvd.JZDataSource
import cn.jzvd.JzvdStd

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/17 23:38
 * @desc
 */
class CustomJzvdStd(context: Context?, attrs: AttributeSet?) : JzvdStd(context, attrs) {

    override fun setUp(jzDataSource: JZDataSource?, screen: Int) {
        super.setUp(jzDataSource, screen)
        //小屏状态下不显示标题，全屏模式下显示标题
//        if (screen == Jzvd.SCREEN_FULLSCREEN) {
//            titleTextView.visibility = View.VISIBLE
//        } else {
//            titleTextView.visibility = View.INVISIBLE
//        }
    }
}