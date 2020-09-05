package com.vicedev.zy_player_android.ui

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.base_list_fragment.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/5 21:53
 * @desc 列表类型的页面父类
 */

abstract class BaseListFragment<T, H : BaseViewHolder> : BaseFragment() {
    private var curPage = 1
    private val listAdapter: BaseQuickAdapter<T, H> by lazy {
        getListAdapter().apply {
            loadMoreModule.run {
                isAutoLoadMore = true
                setOnLoadMoreListener {
                    curPage++
                    requestData()
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.base_list_fragment

    override fun initTitleBar(titleBar: CommonTitleBar?) {
    }

    abstract fun getListAdapter(): BaseLoadMoreAdapter<T, H>

    abstract fun getListLayoutManager(): RecyclerView.LayoutManager

    override fun initView() {
        super.initView()
        rvList.run {
            adapter = listAdapter
            layoutManager = getListLayoutManager()
        }
        statusView.failRetryClickListener = {
            initData()
        }
    }

    override fun initData() {
        super.initData()
        statusView.setLoadingStatus()
        curPage = 1
        listAdapter.loadMoreModule.isEnableLoadMore = true
        requestData()
    }

    private fun requestData() {
        loadData(curPage) {
            try {
                if (!isAdded) return@loadData
                if (it != null) {
                    if (it.isNotEmpty()) {
                        if (curPage == 1) {
                            listAdapter.setNewData(null)
                            statusView.setSuccessStatus()
                        }
                        listAdapter.addData(it)
                    } else {
                        if (curPage == 1) {
                            statusView.setEmptyStatus()
                        }
                        listAdapter.loadMoreModule.isEnableLoadMore = false
                    }
                } else {
                    if (curPage == 1) {
                        statusView.setFailStatus()
                    } else {
                        if (curPage > 1) curPage-- else curPage = 1
                        listAdapter.loadMoreModule.loadMoreFail()
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    abstract fun loadData(page: Int, callback: (list: ArrayList<T>?) -> Unit)
}