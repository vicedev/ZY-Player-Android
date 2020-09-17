package com.vicedev.zy_player_android.ui.reward

import android.app.Activity
import android.content.Intent
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseActivity
import com.vicedev.zy_player_android.ui.search.SEARCH

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/13 0:40
 * @desc 打赏
 */
class RewardActivity : BaseActivity() {
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RewardFragment(), SEARCH)
            .commitAllowingStateLoss()
    }

    override fun getLayoutId(): Int = R.layout.container_layout

    companion object {
        fun jump(activity: Activity) {
            activity.startActivity(Intent(activity, RewardActivity::class.java))
        }
    }

}