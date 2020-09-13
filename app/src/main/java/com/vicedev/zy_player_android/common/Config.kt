package com.vicedev.zy_player_android.common

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SPUtils
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.CommonSource
import org.json.JSONArray

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

    val sourceConfigs: LinkedHashMap<String, SourceConfig> by lazy {
        val configJson = ResourceUtils.readAssets2String("SourceConfig.txt")
        val configArray = JSONArray(configJson)
        val configMap = LinkedHashMap<String, SourceConfig>()
        for (i in 0 until configArray.length()) {
            val config = configArray.getJSONObject(i)
            val key = config.getString("key")
            val name = config.getString("name")
            val api = config.getString("api")
            val download = config.getString("download")
            if (config != null && !key.isNullOrBlank() && !name.isNullOrBlank() && !api.isNullOrBlank()) {
                configMap[key] = SourceConfig(key, name) {
                    CommonSource(key, name, api, download)
                }
            }
        }
        configMap
    }

    /**
     * 根据key获取相应的source
     */
    fun generateSource(key: String?): BaseSource {
        return sourceConfigs[key]?.generateSource() ?: CommonSource("", "", "", "")
    }

    private val defaultSourceKey = "okzy"

    /**
     * 获取当前选择的源
     */
    fun curUseSourceConfig(): BaseSource {
        return generateSource(SPUtils.getInstance().getString("curSourceKey", defaultSourceKey))
    }

    /**
     * 保存当前选择的源
     */
    fun saveCurUseSourceConfig(sourceKey: String?) {
        if (sourceConfigs.containsKey(sourceKey)) {
            SPUtils.getInstance().put("curSourceKey", sourceKey)
        }
    }
}

data class SourceConfig(val key: String, val name: String, val generate: () -> BaseSource) {
    fun generateSource(): BaseSource {
        return generate.invoke()
    }
}