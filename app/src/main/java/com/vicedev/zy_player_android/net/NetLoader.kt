package com.vicedev.zy_player_android.net

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.vicedev.zy_player_android.common.CommonCallback
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.ui.home.model.FilmModel
import com.vicedev.zy_player_android.utils.DataParser

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 23:48
 */
const val TAG_FILM_GET = "tag_film_get"

object NetLoader {

    fun filmGet(key: String, id: Int = 0, page: Int = 1, callback: CommonCallback<FilmModel?>) {
        val configItem = ConfigManager.configMap[key]
        val url = if (id == 0) {
            configItem!!.new.replace("{page}", page.toString())
        } else {
            configItem!!.view.replace("{id}", id.toString())
                .replace("{page}", page.toString())
        }
        cancel(TAG_FILM_GET)
        OkGo.get<String>(url)
            .tag(TAG_FILM_GET)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    callback.onResult(
                        DataParser.parseFilmGet(
                            key,
                            response?.body() ?: "",
                            configItem.type
                        )
                    )
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    callback.onResult(null)
                }

                override fun onFinish() {

                }
            })
    }

    private fun cancel(tag: Any) {
        OkGo.cancelTag(OkGo.getInstance().okHttpClient, tag)
    }
}