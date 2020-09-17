package com.vicedev.zy_player_android.ui

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.base_list_fragment.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/17 15:45
 * @desc 懒加载的通用列表
 */

abstract class BaseLazyListFragment<T, H : BaseViewHolder> : BaseListFragment<T, H>(), LazyLoad {
    override fun initView() {
        super.initView()
        statusView.setLoadingStatus()
    }
}