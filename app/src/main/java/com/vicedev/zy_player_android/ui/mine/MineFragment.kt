package com.vicedev.zy_player_android.ui.mine

import com.blankj.utilcode.util.AppUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.reward.RewardActivity
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
        //打赏一波
        tvReward.setOnClickListener {
            RewardActivity.jump(requireActivity())
        }
        //历史版本下载
        tvHistoryDownload.setOnClickListener {
            XPopup.Builder(requireActivity())
                .asCenterList("历史版本下载", arrayOf("github", "百度网盘，提取码: py2s"),
                    OnSelectListener { position, text ->
                        when (position) {
                            0 -> Utils.openBrowser(
                                requireActivity(),
                                "https://github.com/vicedev/ZY-Player-Android/raw/master/release"
                            )
                            1 -> Utils.openBrowser(
                                requireActivity(),
                                "https://pan.baidu.com/s/1fV1rO-WAcqbf0NBIgm1NsA"
                            )
                        }
                    })
                .show()
        }
        //问题反馈
        tvFeedback.setOnClickListener {
            Utils.openBrowser(requireActivity(), "https://support.qq.com/product/281859")
        }
    }
}
