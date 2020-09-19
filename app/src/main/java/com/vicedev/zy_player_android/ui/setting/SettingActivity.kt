package com.vicedev.zy_player_android.ui.setting

import android.app.Activity
import android.content.Intent
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseActivity

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/13 0:40
 * @desc 设置
 */
const val REWARD = "reward"

class SettingActivity : BaseActivity() {
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SettingFragment(), REWARD)
            .commitAllowingStateLoss()
    }

    override fun getLayoutId(): Int = R.layout.container_layout

    companion object {
        fun jump(activity: Activity) {
            activity.startActivity(Intent(activity, SettingActivity::class.java))
        }
    }

}