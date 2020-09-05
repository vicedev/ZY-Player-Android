package com.vicedev.zy_player_android.ui.channel

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.OKZYWSource
import com.vicedev.zy_player_android.sources.bean.HomeChannelData
import com.vicedev.zy_player_android.ui.BaseListFragment
import com.vicedev.zy_player_android.ui.channel.adapter.HomeChannelAdapter

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 23:31
 * @desc 首页频道页
 */
const val TID = "tid"

class HomeChannelFragment : BaseListFragment<HomeChannelData, BaseViewHolder>() {

    private var source: BaseSource? = null
    private lateinit var tid: String

    companion object {
        fun instance(tid: String): HomeChannelFragment {
            return HomeChannelFragment().apply {
                arguments = bundleOf(TID to tid)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = OKZYWSource()
        tid = arguments?.getString(TID).textOrDefault()
    }

    override fun getListAdapter(): BaseLoadMoreAdapter<HomeChannelData, BaseViewHolder> {
        return HomeChannelAdapter()
    }

    override fun getListLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
    }

    override fun loadData(page: Int, callback: (list: ArrayList<HomeChannelData>?) -> Unit) {
        source?.requestHomeChannelData(page, tid) {
            callback.invoke(it)
        }
    }


}