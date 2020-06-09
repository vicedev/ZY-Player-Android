package com.vicedev.zy_player_android.net

import com.blankj.utilcode.util.ToastUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.vicedev.zy_player_android.common.CommonCallback
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.ui.home.model.FilmModel

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 23:48
 */
const val TAG_FILM_GET = "tag_film_get"

object NetLoader {

    /**
     * 最新数据
     */
    fun filmGet(
        key: String,
        id: Int = 0,
        page: Int = 1,
        callback: CommonCallback<FilmModel?>
    ) {
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

    /**
     * 搜索数据
     */
    fun searchGet(
        key: String,
        keywords: String,
        page: Int = 1,
        callback: CommonCallback<FilmModel?>
    ) {
        val configItem = ConfigManager.configMap[key]
        if (configItem!!.search.isBlank()) {
            ToastUtils.showShort("该视频源不支持搜索")
            callback.onResult(null)
            return
        }
        val url = if (configItem.type == 0) {
            configItem.search.replace("{page}", page.toString()).replace("{keywords}", keywords)
        } else {
            configItem.search.replace("{keywords}", keywords)
        }
        cancel(TAG_FILM_GET)
        OkGo.get<String>(url)
            .tag(TAG_FILM_GET)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    callback.onResult(
                        DataParser.parseSearchGet(
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