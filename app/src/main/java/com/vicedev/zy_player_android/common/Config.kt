package com.vicedev.zy_player_android.common

import com.blankj.utilcode.util.GsonUtils
import com.vicedev.zy_player_android.App

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
        val open = App.appContext.assets.open("config.txt");
        val size = open.available()
        val byteArray = ByteArray(size)
        open.read(byteArray)
        open.close()
        val json = String(byteArray, Charsets.UTF_8)
        GsonUtils.fromJson(json, Config::class.java)
    }
    val configMap: HashMap<String, ConfigItem> by lazy {
        val hashMap = hashMapOf<String, ConfigItem>()
        config.forEach {
            hashMap[it.key] = it
        }
        hashMap
    }
}