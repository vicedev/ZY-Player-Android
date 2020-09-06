package com.vicedev.zy_player_android.common

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.OKZYWSource

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

    const val OKZYW = "okzy"

    val sourceConfigs: HashMap<String, SourceConfig> by lazy {
        hashMapOf(OKZYW to SourceConfig(OKZYW, "OK 资源网") { OKZYWSource() })
    }

    /**
     * 根据key获取相应的source
     */
    fun generateSource(key: String): BaseSource {
        return when (key) {
            OKZYW -> sourceConfigs[OKZYW]?.generateSource()
            else -> sourceConfigs[OKZYW]?.generateSource()
        } ?: OKZYWSource()
    }
}

data class SourceConfig(val key: String, val name: String, val generate: () -> BaseSource) {
    fun generateSource(): BaseSource {
        return generate.invoke()
    }
}