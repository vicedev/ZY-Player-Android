package com.vicedev.zy_player_android.ui.channel.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.bean.HomeChannelData

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/5 22:51
 * @desc 首页频道的适配器
 */

class HomeChannelAdapter :
    BaseLoadMoreAdapter<HomeChannelData, BaseViewHolder>(
        R.layout.home_channel_item_layout
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: HomeChannelData) {
        holder.setText(R.id.tvTitle, item.name.textOrDefault("--"))
        Glide.with(context)
            .load(item.pic)
            .centerCrop()
//            .transform(CenterCrop(), RoundedCorners(ConvertUtils.dp2px(12.0f)))
            .into(holder.getView(R.id.ivPiv))
    }

}

