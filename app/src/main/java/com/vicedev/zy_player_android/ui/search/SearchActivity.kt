package com.vicedev.zy_player_android.ui.search

import android.app.Activity
import android.content.Intent
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseActivity

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/7 21:22
 * @desc 搜索页
 */
const val SEARCH = "search"

class SearchActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.container_layout
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SearchFragment(), SEARCH)
            .commitAllowingStateLoss()
    }

    companion object {
        fun jump(activity: Activity) {
            activity.startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}