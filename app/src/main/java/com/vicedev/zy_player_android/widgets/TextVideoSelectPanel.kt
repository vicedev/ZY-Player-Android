package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.textOrDefault

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/19 13:02
 * @desc 简单文字型
 */
class TextVideoSelectPanel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseVideoSelectPanel<String>(context, attrs, defStyleAttr) {

    override fun getAdapter(): BaseQuickAdapter<String, BaseViewHolder> {
        return MyAdapter()
    }

    inner class MyAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.video_select_panel_item) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tvName, item.textOrDefault())
        }
    }
}