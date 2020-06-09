package com.vicedev.zy_player_android.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
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
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_SEARCH_DELETE -> {
                        //删除按钮
                        ToastUtils.showShort("删除")
                    }
                }
                if (extra != null) {
                    ToastUtils.showShort(extra)
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
        searchSelectView.onSelectListener = { key, id ->
            curKey = key
            curId = id
            loadData(true)
        }
    }

    private fun loadData(init: Boolean) {
        if (init) {
            curPage = 1
            statusView.setLoadingStatus()
        } else {
            ++curPage
        }
        NetLoader.filmGet(curKey, curId, curPage, object : CommonCallback<FilmModel?> {
            override fun onResult(t: FilmModel?) {
                if (init) {
                    filmList.clear()
                }
                t?.let {
                    if (it.filmModelItemList.isNullOrEmpty()) {
                        statusView.setEmptyStatus()
                    } else {
                        filmList.addAll(it.filmModelItemList)
                        statusView.setSuccessStatus()
                    }
                } ?: let {
                    curPage--
                    statusView.setFailStatus()
                }
                filmAdapter.notifyDataSetChanged()
                if (filmAdapter.loadMoreModule.isLoading) {
                    filmAdapter.loadMoreModule.loadMoreComplete()
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        //设置搜索选择视图的数据，会回调选择监听
        searchSelectView.initData()
    }
}
