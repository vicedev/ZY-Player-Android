package com.vicedev.zy_player_android.ui.reward

import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/13 0:33
 * @desc 打赏
 */
class RewardFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_reward

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerTextView.text = "打赏一波"

            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_LEFT_BUTTON -> {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

}