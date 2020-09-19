package com.vicedev.zy_player_android.ui.home

import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.SP_OPEN_FL
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.event.OpenFLEvent
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.search.SearchActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_home_new.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 22:39
 */
class NewHomeFragment : BaseFragment() {
    private var source: BaseSource? = null

    private var selectSourceDialog: BasePopupView? = null

    private var openFL = false

    override fun getLayoutId(): Int = R.layout.fragment_home_new

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerSearchRightImageView.gone()
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_SEARCH -> {
                        SearchActivity.jump(requireActivity())
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = ConfigManager.curUseSourceConfig()

        openFL = SPUtils.getInstance().getBoolean(SP_OPEN_FL)
    }

    override fun initListener() {
        super.initListener()
        faBtn.setOnClickListener {
            //选择视频源
            if (selectSourceDialog == null) {
                val values = ConfigManager.sourceConfigs.values
                val keys = ConfigManager.sourceConfigs.keys.toTypedArray()
                selectSourceDialog = XPopup.Builder(requireActivity())
                    .asCenterList("选择视频源",
                        values.map { it.name }.toTypedArray(),
                        null,
                        keys.indexOfFirst { it == source?.key },
                        OnSelectListener { position, text ->
                            source =
                                ConfigManager.generateSource(keys[position])
                            ConfigManager.saveCurUseSourceConfig(source?.key)
                            initData()
                        })
                    .bindLayout(R.layout.xpopup_center_impl_list)
            }
            selectSourceDialog?.show()
        }
    }

    override fun initData() {
        super.initData()
        titleBar?.centerSearchEditText?.hint = source?.name.textOrDefault("搜索")
        childFragmentManager
            .beginTransaction()
            .replace(R.id.flContainer, HomeSourceFragment())
            .commitNowAllowingStateLoss()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(event: OpenFLEvent) {
        if (openFL == event.open) {
            //与当前一样，return
            return
        }
        openFL = event.open
        //不一样时重新加载数据
        initData()
    }

}