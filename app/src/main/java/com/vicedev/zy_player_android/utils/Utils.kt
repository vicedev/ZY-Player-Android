package com.vicedev.zy_player_android.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.ToastUtils
import fr.arnaudguyon.xmltojsonlib.XmlToJson

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/10 22:37
 */
object Utils {
    /**
     * 打开外部浏览器
     */
    fun openBrowser(context: Context, url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"))
        } else {
            ToastUtils.showShort("没有可用浏览器")
        }
    }

    /**
     * xml转json
     */
    fun xmlToJson(xmlString: String?): XmlToJson? {
        try {
            return XmlToJson.Builder(xmlString!!).build()
        } catch (e: Exception) {
        }
        return null
    }
}