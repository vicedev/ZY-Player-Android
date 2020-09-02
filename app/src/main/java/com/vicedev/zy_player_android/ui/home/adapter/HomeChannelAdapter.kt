package com.vicedev.zy_player_android.ui.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.home.model.FilmModelItem

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 23:43
 * @desc 首页频道列表
 */
class HomeChannelAdapter(data: MutableList<FilmModelItem>) :
    BaseQuickAdapter<FilmModelItem, BaseViewHolder>(
        R.layout.film_item_layout, data
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: FilmModelItem) {
    }
}