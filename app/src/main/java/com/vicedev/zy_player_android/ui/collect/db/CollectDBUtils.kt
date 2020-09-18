package com.vicedev.zy_player_android.ui.collect.db

import com.blankj.utilcode.util.ThreadUtils
import com.vicedev.zy_player_android.common.Task
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

        ThreadUtils.executeByCached(Task<Boolean>({
            save(collectDBModel)
        }, {
            callback?.invoke(it ?: false)
        }))
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
        ThreadUtils.executeByCached(Task<ArrayList<CollectDBModel>?>({
            searchAll()
        }, {
            callback?.invoke(it)
        }))
    }

    fun search(uniqueKey: String?): CollectDBModel? {
        if (uniqueKey.isNullOrBlank()) return null
        return LitePal.where("uniqueKey = ?", uniqueKey).findFirst(CollectDBModel::class.java)
    }

    fun searchAsync(uniqueKey: String?, callback: ((CollectDBModel?) -> Unit)?) {

        ThreadUtils.executeByCached(Task<CollectDBModel?>({
            search(uniqueKey)
        }, {
            callback?.invoke(it)
        }))
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
