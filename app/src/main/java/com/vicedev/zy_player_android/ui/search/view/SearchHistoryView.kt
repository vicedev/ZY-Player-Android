package com.vicedev.zy_player_android.ui.search.view

import android.content.Context
import com.lxj.xpopup.core.BasePopupView
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.db.SearchHistoryDBUtils
import kotlinx.android.synthetic.main.search_history_view.view.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/12 17:01
 * @desc 搜索历史
 */
class SearchHistoryView constructor(
    context: Context
) : BasePopupView(context) {

    init {
        ivDelete.setOnClickListener {
            //清除全部记录
            if (SearchHistoryDBUtils.deleteAll()) {
                tagGroup.setTags(arrayListOf())
                statusView.setEmptyStatus()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        statusView.setLoadingStatus()

        SearchHistoryDBUtils.searchAllAsync {
            if (it.isNullOrEmpty()) {
                statusView.setEmptyStatus()
            } else {
                tagGroup.setTags(it.map { model -> model.searchWord })

                statusView.setSuccessStatus()
            }
        }
    }

    override fun getPopupLayoutId(): Int {
        return R.layout.search_history_view
    }
}