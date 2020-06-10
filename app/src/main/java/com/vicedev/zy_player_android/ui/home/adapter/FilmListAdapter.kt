package com.vicedev.zy_player_android.ui.home.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.detail.FilmDetailActivity
import com.vicedev.zy_player_android.ui.detail.FilmDetailFragment
import com.vicedev.zy_player_android.ui.home.model.FilmModelItem

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 9:39
 */
class FilmListAdapter(data: MutableList<FilmModelItem>) :
    BaseQuickAdapter<FilmModelItem, BaseViewHolder>(
        R.layout.film_item_layout, data
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: FilmModelItem) {
        holder.setText(R.id.tvFilm, item.name)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, FilmDetailActivity::class.java).apply {
                putExtra(FilmDetailFragment.KEY, item.site)
                putExtra(FilmDetailFragment.DETAIL_URL, item.detail)
            })
        }
    }
}