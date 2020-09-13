package com.vicedev.zy_player_android.ui.search

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.KeyboardUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.db.SearchHistoryDBUtils
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.search.view.SearchHistoryView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/7 21:19
 * @desc 搜索页
 */

class SearchFragment : BaseFragment() {

    private var sourceKey: String? = null

    private var searchWord: String = ""

    private var selectSourceDialog: BasePopupView? = null

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_SEARCH_DELETE -> {
                        //删除按钮
                    }
                    CommonTitleBar.ACTION_LEFT_BUTTON -> {
                        requireActivity().finish()
                    }
                }
                if (extra != null) {
                    //按下键盘搜索按钮
                    initData()
                    KeyboardUtils.hideSoftInput(requireActivity())
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sourceKey = ConfigManager.curUseSourceConfig().key
    }

    override fun initView() {
        super.initView()
        changeEditHint()
    }

    override fun initListener() {
        super.initListener()
        faBtnExchange.setOnClickListener {
            //选择视频源
            if (selectSourceDialog == null) {
                val values = ConfigManager.sourceConfigs.values
                val keys = ConfigManager.sourceConfigs.keys.toTypedArray()
                selectSourceDialog = XPopup.Builder(requireActivity())
                    .asCenterList("选择视频源",
                        values.map { it.name }.toTypedArray(),
                        null,
                        keys.indexOfFirst { it == sourceKey },
                        OnSelectListener { position, text ->
                            sourceKey = keys[position]
                            ConfigManager.saveCurUseSourceConfig(text)
                            initData()
                        })
                    .bindLayout(R.layout.xpopup_center_impl_list)

            }
            selectSourceDialog?.show()
        }

        faBtnHistory.setOnClickListener {
            //搜索历史
            showSearchHistory()
        }

        //监听搜索框变化
        titleBar?.centerSearchEditText?.addTextChangedListener {
            searchWord = it?.toString() ?: ""
        }
    }

    override fun initData() {
        super.initData()
        if (searchWord.isBlank()) {
            return
        }
        SearchHistoryDBUtils.saveAsync(searchWord)
        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.flContainer,
                SearchResultFragment.instance(sourceKey!!, searchWord),
                SEARCH_RESULT
            )
            .commitAllowingStateLoss()
    }

    private fun changeEditHint() {
        titleBar?.centerSearchEditText?.hint =
            ConfigManager.sourceConfigs[sourceKey]?.name.textOrDefault("搜索")
    }

    private fun showSearchHistory() {
        val searchHistoryView = SearchHistoryView(requireActivity())

        val dialog = XPopup.Builder(requireActivity())
            .asCustom(searchHistoryView)

        searchHistoryView.onSelectListener = {
            searchWord = it
            titleBar?.centerSearchEditText?.setText(it)
            initData()

            dialog.dismiss()
        }

        dialog.show()
    }
}