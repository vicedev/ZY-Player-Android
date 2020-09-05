package com.vicedev.zy_player_android.common

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/5 23:13
 * @desc 能上拉加载的Adapter
 */
abstract class BaseLoadMoreAdapter<T, H : BaseViewHolder>(
    private val layoutResId: Int,
    data: MutableList<T>? = null
) : BaseQuickAdapter<T, H>(layoutResId, data), LoadMoreModule