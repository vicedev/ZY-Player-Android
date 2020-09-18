package com.vicedev.zy_player_android.db

import com.blankj.utilcode.util.ThreadUtils
import com.vicedev.zy_player_android.common.Task
import org.litepal.LitePal
import java.util.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/12 20:55
 * @desc 搜索历史的数据库操作
 */

object SearchHistoryDBUtils {
    fun saveAsync(searchWord: String, callback: ((Boolean) -> Unit)? = null) {

        ThreadUtils.executeByCached(Task<Boolean>({
            save(searchWord)
        }, {
            callback?.invoke(it ?: false)
        }))
    }


    fun save(searchWord: String): Boolean {
        if (searchWord.isBlank()) {
            return false
        }
        LitePal.where("searchWord = ?", searchWord).findFirst(SearchHistoryDBModel::class.java)
            ?.delete()
        val searchHistoryDBModel = SearchHistoryDBModel()
        searchHistoryDBModel.searchWord = searchWord
        searchHistoryDBModel.updateData = Date()
        return searchHistoryDBModel.save()
    }

    fun searchAll(): ArrayList<SearchHistoryDBModel>? {
        val list = LitePal.where("searchWord not null").order("updateData")
            .find(SearchHistoryDBModel::class.java)
        list?.reverse()
        return list as? ArrayList<SearchHistoryDBModel>?
    }

    fun searchAllAsync(callback: ((ArrayList<SearchHistoryDBModel>?) -> Unit)?) {

        ThreadUtils.executeByCached(Task<ArrayList<SearchHistoryDBModel>?>({
            searchAll()
        }, {
            callback?.invoke(it)
        }))

    }

    fun deleteAll(): Boolean {
        return LitePal.deleteAll(SearchHistoryDBModel::class.java) > 0
    }
}