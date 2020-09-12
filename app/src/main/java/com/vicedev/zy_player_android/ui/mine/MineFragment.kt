package com.vicedev.zy_player_android.ui.mine

import com.blankj.utilcode.util.AppUtils
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.utils.Utils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerTextView.text = "我的"
        }
    }

    override fun initView() {
        super.initView()
        tvVersion.text = "版本 ${AppUtils.getAppVersionName()}"
    }

    override fun initListener() {
        super.initListener()
        //赏个star
        tvStar.setOnClickListener {
            Utils.openBrowser(requireActivity(), "https://github.com/vicedev/ZY-Player-Android")
        }
    }
}
