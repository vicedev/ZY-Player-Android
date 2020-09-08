package com.vicedev.zy_player_android.ui.search.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.bean.SearchResultData

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/5 22:51
 * @desc 搜索结果页的适配器
 */

class SearchResultAdapter :
    BaseLoadMoreAdapter<SearchResultData, BaseViewHolder>(
        R.layout.search_result_item_layout
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: SearchResultData) {
        holder.setText(R.id.tvName, item.name.textOrDefault("--"))
        holder.setText(R.id.tvType, item.type.textOrDefault("--"))
    }

}

