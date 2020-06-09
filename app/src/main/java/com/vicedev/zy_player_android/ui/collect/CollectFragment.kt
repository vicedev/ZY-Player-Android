package com.vicedev.zy_player_android.ui.collect

import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

class CollectFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerTextView.text = "收藏"
        }
    }

}
