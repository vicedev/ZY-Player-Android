package com.vicedev.zy_player_android.ui.channel

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.GridSpaceItemDecoration
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.HomeChannelData
import com.vicedev.zy_player_android.ui.BaseListFragment
import com.vicedev.zy_player_android.ui.channel.adapter.HomeChannelAdapter
import com.vicedev.zy_player_android.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.base_list_fragment.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 23:31
 * @desc 首页频道页
 */
const val TID = "tid"

class HomeChannelFragment : BaseListFragment<HomeChannelData, BaseViewHolder>() {

    private lateinit var source: BaseSource
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
        source = ConfigManager.curUseSourceConfig()
        tid = arguments?.getString(TID).textOrDefault()
    }

    override fun initView() {
        super.initView()
        rvList.addItemDecoration(
            GridSpaceItemDecoration(ConvertUtils.dp2px(12.0f), true)
        )
    }

    override fun getListAdapter(): BaseLoadMoreAdapter<HomeChannelData, BaseViewHolder> {
        return HomeChannelAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                DetailActivity.jump(
                    requireActivity(),
                    source.key,
                    data[position].id.textOrDefault()
                )
            }
        }
    }

    override fun getListLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
    }

    override fun loadData(page: Int, callback: (list: ArrayList<HomeChannelData>?) -> Unit) {
        source.requestHomeChannelData(page, tid) {
            callback.invoke(it)
        }
    }


}