package com.vicedev.zy_player_android.ui.setting

import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.utils.Utils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerTextView.text = "设置"
        }
    }

    override fun initListener() {
        super.initListener()
        //赏个star
        tvStar.setOnClickListener {
            Utils.openBrowser(requireActivity(), "https://github.com/vicedev/ZY-Player-Android")
        }
    }
}
