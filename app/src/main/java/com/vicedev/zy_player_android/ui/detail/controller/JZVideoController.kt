package com.vicedev.zy_player_android.ui.detail.controller

import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.vicedev.zy_player_android.widgets.CustomJzvdStd

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/17 22:38
 * @desc JZVideo的控制类
 */
class JZVideoController {

    private lateinit var player: CustomJzvdStd

    fun init(player: CustomJzvdStd) {
        this.player = player
    }

    fun onBackPressed(): Boolean {
        return Jzvd.backPress()
    }

    fun onResume() {
        JzvdStd.goOnPlayOnResume();
    }

    fun onPause() {
        JzvdStd.goOnPlayOnPause();
    }

    fun onDestroy() {
        JzvdStd.releaseAllVideos()
    }

    fun play(playUrl: String?, name: String?) {
        player.setUp(playUrl, name)
        player.startVideo()
    }
}