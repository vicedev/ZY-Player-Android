package com.vicedev.zy_player_android.ui.collect

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lxj.xpopup.XPopup
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.common.visible
import com.vicedev.zy_player_android.ui.BaseListFragment
import com.vicedev.zy_player_android.ui.collect.db.CollectDBModel
import com.vicedev.zy_player_android.ui.collect.db.CollectDBUtils
import com.vicedev.zy_player_android.ui.detail.DetailActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.base_list_fragment.*

class CollectFragment : BaseListFragment<CollectDBModel, BaseViewHolder>() {

    private var isInit: Boolean = false

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            visible()
            centerTextView.text = "收藏"
        }
    }

    override fun getListAdapter(): BaseLoadMoreAdapter<CollectDBModel, BaseViewHolder> {
        return CollectAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                DetailActivity.jump(
                    requireActivity(),
                    data[position].sourceKey.textOrDefault(),
                    data[position].videoId.textOrDefault()
                )
            }
        }
    }

    override fun getListLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(requireActivity())
    }

    override fun loadData(page: Int, callback: (list: ArrayList<CollectDBModel>?) -> Unit) {
        if (page == 1) {
            CollectDBUtils.searchAllAsync {
                callback.invoke(it)
                isInit = true
            }
        } else {
            callback.invoke(arrayListOf())
        }
    }

    fun refresh(){
        if (isInit){
            initData()
        }
    }

    inner class CollectAdapter :
        BaseLoadMoreAdapter<CollectDBModel, BaseViewHolder>(
            R.layout.collect_item_layout
        ) {
        override fun convert(holder: BaseViewHolder, item: CollectDBModel) {
            holder.setText(R.id.tvName, item.name)
            holder.setText(R.id.tvSourceName, item.sourceName)

            holder.getView<View>(R.id.ivDelete).setOnClickListener {
                XPopup.Builder(context)
                    .asConfirm("", "确认删除吗") {
                        try {
                            val delete = CollectDBUtils.delete(item.uniqueKey)
                            if (delete) {
                                remove(item)
                                if (data.isEmpty()) {
                                    statusView.setEmptyStatus()
                                }
                            } else {
                                ToastUtils.showShort("删除失败")
                            }
                        } catch (e: Exception) {
                            ToastUtils.showShort("删除失败")
                        }
                    }
                    .show()
            }
        }
    }

}
