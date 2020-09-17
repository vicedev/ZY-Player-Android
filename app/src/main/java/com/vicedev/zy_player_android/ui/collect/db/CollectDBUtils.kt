package com.vicedev.zy_player_android.ui.collect.db

import com.blankj.utilcode.util.ThreadUtils
import org.litepal.LitePal
import java.util.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/12 20:55
 * @desc 收藏的数据库操作工具类
 */

object CollectDBUtils {
    fun saveAsync(collectDBModel: CollectDBModel, callback: ((Boolean) -> Unit)? = null) {
        ThreadUtils.executeByCached(object : ThreadUtils.Task<Boolean>() {
            override fun doInBackground(): Boolean {
                return save(collectDBModel)
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


    fun save(collectDBModel: CollectDBModel): Boolean {
        if (collectDBModel.uniqueKey.isNullOrBlank() || collectDBModel.videoId.isNullOrBlank() || collectDBModel.sourceKey.isNullOrBlank()) {
            return false
        }
        val videoId = collectDBModel.videoId
        LitePal.where("videoId = ?", videoId).findFirst(collectDBModel::class.java)?.delete()
        return collectDBModel.save()
    }

    fun searchAll(): ArrayList<CollectDBModel>? {
        return LitePal.findAll(CollectDBModel::class.java) as? ArrayList<CollectDBModel>
    }

    fun searchAllAsync(callback: ((ArrayList<CollectDBModel>?) -> Unit)?) {
        ThreadUtils.executeByCached(object : ThreadUtils.Task<ArrayList<CollectDBModel>?>() {
            override fun doInBackground(): ArrayList<CollectDBModel>? {
                return searchAll()
            }

            override fun onSuccess(result: ArrayList<CollectDBModel>?) {
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

    fun search(uniqueKey: String?): CollectDBModel? {
        if (uniqueKey.isNullOrBlank()) return null
        return LitePal.where("uniqueKey = ?", uniqueKey).findFirst(CollectDBModel::class.java)
    }

    fun searchAsync(uniqueKey: String?, callback: ((CollectDBModel?) -> Unit)?) {
        ThreadUtils.executeByCached(object : ThreadUtils.Task<CollectDBModel?>() {
            override fun doInBackground(): CollectDBModel? {
                return search(uniqueKey)
            }

            override fun onSuccess(result: CollectDBModel?) {
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
        return LitePal.deleteAll(CollectDBModel::class.java) > 0
    }

    fun delete(uniqueKey: String?): Boolean {
        if (uniqueKey.isNullOrBlank()) return false

        return LitePal.where("uniqueKey = ?", uniqueKey).findFirst(CollectDBModel::class.java)
            ?.delete() ?: 0 > 0
    }
}