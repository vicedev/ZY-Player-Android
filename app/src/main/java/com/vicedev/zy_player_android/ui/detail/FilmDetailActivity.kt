package com.vicedev.zy_player_android.ui.detail

import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseActivity

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 17:49
 */
const val FILM_DETAIL = "film_detail"

class FilmDetailActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.container_layout
    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, FilmDetailFragment(), FILM_DETAIL)
            .commitAllowingStateLoss()
    }
}