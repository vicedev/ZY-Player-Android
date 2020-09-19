package com.vicedev.zy_player_android.ui.setting

import com.blankj.utilcode.util.SPUtils
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.SP_OPEN_FL
import com.vicedev.zy_player_android.event.OpenFLEvent
import com.vicedev.zy_player_android.ui.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.set_item_view.view.*
import org.greenrobot.eventbus.EventBus

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/19 16:57
 * @desc 设置页面
 */
class SettingFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerTextView.text = "设置"

            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_LEFT_BUTTON -> {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        //开启福利
        val openFL = SPUtils.getInstance().getBoolean(SP_OPEN_FL)
        setOpenFL.switchBtn.isChecked = openFL
    }

    override fun initListener() {
        super.initListener()
        //开启福利开关
        setOpenFL.switchBtn.setOnCheckedChangeListener { view, isChecked ->
            SPUtils.getInstance().put(SP_OPEN_FL, isChecked)
            EventBus.getDefault().removeStickyEvent(OpenFLEvent(isChecked))
            EventBus.getDefault().postSticky(OpenFLEvent(isChecked))
        }
    }

}