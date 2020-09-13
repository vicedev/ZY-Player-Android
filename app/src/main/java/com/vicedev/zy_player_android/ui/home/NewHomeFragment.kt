package com.vicedev.zy_player_android.ui.home

import android.os.Bundle
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.search.SearchActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_new.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 22:39
 */
class NewHomeFragment : BaseFragment() {
    private var source: BaseSource? = null

    private var selectSourceDialog: BasePopupView? = null

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

}