package com.vicedev.zy_player_android.db

import com.blankj.utilcode.util.ThreadUtils
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
        ThreadUtils.executeByCached(object : ThreadUtils.Task<Boolean>() {
            override fun doInBackground(): Boolean {
                return save(searchWord)
            }

            override fun onSuccess(result: Boolean?) {
                callback?.invoke(result ?: false)
            }

            override fun onFail(t: Throwable?) {
                callback?.invoke(false)
            }

            override fun onCancel() {
                callback?.invoke(false)
            }

        })
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
        ThreadUtils.executeByCached(object : ThreadUtils.Task<ArrayList<SearchHistoryDBModel>?>() {
            override fun doInBackground(): ArrayList<SearchHistoryDBModel>? {
                return searchAll()
            }

            override fun onSuccess(result: ArrayList<SearchHistoryDBModel>?) {
                callback?.invoke(result)
            }

            override fun onFail(t: Throwable?) {
                callback?.invoke(null)
            }

            override fun onCancel() {
                callback?.invoke(null)
            }
        })
    }

    fun deleteAll(): Boolean {
        return LitePal.deleteAll(SearchHistoryDBModel::class.java) > 0
    }
}