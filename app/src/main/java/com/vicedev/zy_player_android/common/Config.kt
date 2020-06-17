package com.vicedev.zy_player_android.common

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 17:37
 */
class Config : ArrayList<ConfigItem>()

data class ConfigItem(
    val key: String,
    val name: String,
    val new: String,
    val search: String,
    val tags: List<Tag>,
    val type: Int,
    val url: String,
    val view: String
)

data class Tag(
    val children: List<Children>,
    val id: Int,
    val title: String
)

data class Children(
    val id: Int,
    val title: String
)

object ConfigManager {
    val config: Config by lazy {
        GsonUtils.fromJson(ResourceUtils.readAssets2String("config.txt"), Config::class.java)
    }
    val configMap: HashMap<String, ConfigItem> by lazy {
        val hashMap = hashMapOf<String, ConfigItem>()
        config.forEach {
            hashMap[it.key] = it
        }
        hashMap
    }
}