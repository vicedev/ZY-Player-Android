package com.vicedev.zy_player_android.ui.detail

import android.app.Activity
import android.content.Intent
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.ui.BaseActivity

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/8 21:48
 * @desc 详情页
 */

const val DETAIL = "detail"

class DetailActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.container_layout
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                DetailFragment.instance(
                    intent.getStringExtra(SOURCE_KEY).textOrDefault(),
                    intent.getStringExtra(ID).textOrDefault()
                ),
                DETAIL
            )
            .commitAllowingStateLoss()
    }

    companion object {
        fun jump(activity: Activity, sourceKey: String, id: String) {
            activity.startActivity(Intent(activity, DetailActivity::class.java).apply {
                putExtra(SOURCE_KEY, sourceKey)
                putExtra(ID, id)
            })
        }
    }
}