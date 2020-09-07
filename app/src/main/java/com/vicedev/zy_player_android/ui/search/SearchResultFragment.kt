package com.vicedev.zy_player_android.ui.search

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.vicedev.zy_player_android.common.BaseLoadMoreAdapter
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.SearchResultData
import com.vicedev.zy_player_android.ui.BaseListFragment
import com.vicedev.zy_player_android.ui.search.adapter.SearchResultAdapter

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/7 21:19
 * @desc 搜索结果页
 */

const val SOURCE_KEY = "source_key"
const val SEARCH_WORD = "search_word"

class SearchResultFragment : BaseListFragment<SearchResultData, BaseViewHolder>() {

    private lateinit var source: BaseSource
    private lateinit var searchWord: String

    companion object {
        fun instance(sourceKey: String, searchWord: String): SearchResultFragment {
            return SearchResultFragment().apply {
                arguments = bundleOf(SOURCE_KEY to sourceKey, SEARCH_WORD to searchWord)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = ConfigManager.generateSource(arguments?.getString(SOURCE_KEY).textOrDefault())
        searchWord = arguments?.getString(SEARCH_WORD).textOrDefault()
    }

    override fun getListAdapter(): BaseLoadMoreAdapter<SearchResultData, BaseViewHolder> {
        return SearchResultAdapter()
    }

    override fun getListLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun loadData(page: Int, callback: (list: ArrayList<SearchResultData>?) -> Unit) {
        if (searchWord.isBlank()) {
            callback.invoke(arrayListOf())
        } else {
            source.requestSearchData(searchWord, page) {
                callback.invoke(it)
            }
        }
    }

}