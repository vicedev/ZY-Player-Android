package com.vicedev.zy_player_android.ui.home

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.CommonCallback
import com.vicedev.zy_player_android.net.NetLoader
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.home.adapter.FilmListAdapter
import com.vicedev.zy_player_android.ui.home.model.FilmModel
import com.vicedev.zy_player_android.ui.home.model.FilmModelItem
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private var curPage: Int = 1
    private var curKey: String = ""
    private var curId: Int = 0
    private var keywords: String = ""

    private val filmAdapter by lazy {
        FilmListAdapter(filmList).apply {
            loadMoreModule.run {
                isAutoLoadMore = true
                setOnLoadMoreListener {
                    loadData(false)
                }
            }
        }
    }
    private val filmList = ArrayList<FilmModelItem>()

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerSearchEditText.hint = "输入搜索内容"
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_SEARCH_DELETE -> {
                        //删除按钮
                    }
                }
                if (extra != null) {
                    //按下键盘搜索按钮
                    loadData(true)
                    KeyboardUtils.hideSoftInput(requireActivity())
                }
            }
        }
    }


    override fun initView() {
        super.initView()

        rvMovies.run {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = filmAdapter
        }

        statusView.failRetryClickListener = {
            loadData(true)
        }

    }

    override fun initListener() {
        super.initListener()
        //搜索参数选择
        searchSelectView.onSelectListener = { key, id ->
            curKey = key
            curId = id
            loadData(true)
        }

        //监听搜索框变化
        titleBar!!.centerSearchEditText.addTextChangedListener {
            keywords = it?.toString() ?: ""
            if (keywords.isBlank()) {
                searchSelectView.showAll()
            } else {
                searchSelectView.onlyShowSite()
            }
        }
    }

    private fun loadData(init: Boolean) {
        if (init) {
            curPage = 1
            statusView.setLoadingStatus()
        } else {
            ++curPage
        }
        loadDataByType(object : CommonCallback<FilmModel?> {
            override fun onResult(t: FilmModel?) {
                if (init) {
                    filmAdapter.loadMoreModule.isEnableLoadMore = true
                    filmList.clear()
                    when {
                        t == null -> {
                            statusView.setFailStatus()
                        }
                        t.filmModelItemList.isNullOrEmpty() -> {
                            statusView.setEmptyStatus()
                        }
                        else -> {
                            filmList.addAll(t.filmModelItemList)
                            statusView.setSuccessStatus()
                            if (t.total > 0 && t.total <= filmList.size) {
                                filmAdapter.loadMoreModule.isEnableLoadMore = false
                            }
                        }
                    }
                } else {
                    when {
                        t == null -> {
                            curPage--
                            filmAdapter.loadMoreModule.loadMoreFail()
                        }
                        t.filmModelItemList.isNullOrEmpty() -> {
                            filmAdapter.loadMoreModule.isEnableLoadMore = false
                        }
                        else -> {
                            filmList.addAll(t.filmModelItemList)
                            if (t.total > 0 && t.total <= filmList.size) {
                                filmAdapter.loadMoreModule.isEnableLoadMore = false
                            } else {
                                filmAdapter.loadMoreModule.loadMoreComplete()
                            }
                        }
                    }
                }
                filmAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun loadDataByType(callback: CommonCallback<FilmModel?>) {
        if (!StringUtils.isEmpty(titleBar!!.centerSearchEditText.text)) {
            NetLoader.searchGet(curKey, keywords, curPage, callback)
        } else {
            NetLoader.filmGet(curKey, curId, curPage, callback)
        }
    }

    override fun initData() {
        super.initData()
        //设置搜索选择视图的数据，会回调选择监听
        searchSelectView.initData()
    }
}
