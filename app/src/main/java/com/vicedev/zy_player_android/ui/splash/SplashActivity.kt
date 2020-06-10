package com.vicedev.zy_player_android.ui.splash

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ThreadUtils
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseActivity
import com.vicedev.zy_player_android.ui.MainActivity
import com.wuhenzhizao.titlebar.statusbar.StatusBarUtils

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/10 23:20
 */
class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.transparentStatusBar(window)

        ThreadUtils.getMainHandler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 500)
    }
}