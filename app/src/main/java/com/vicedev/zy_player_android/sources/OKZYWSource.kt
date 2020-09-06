package com.vicedev.zy_player_android.sources

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.sources.bean.HomeChannelData
import com.vicedev.zy_player_android.sources.bean.HomeData

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 21:47
 * @desc OK 资源网
 */

class OKZYWSource(
    override val baseUrl: String = "http://cj.okzy.tv/inc/api.php",
    override val downloadBaseUrl: String = "http://cj.okzy.tv/inc/apidown.php",
    override val name: String = "OK 资源网"
) : BaseSource() {

    override fun requestHomeData(callback: (t: HomeData?) -> Unit) {
        OkGo.get<String>(baseUrl)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    callback.invoke(parseHomeData1(response?.body()))
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    callback.invoke(null)
                }
            })
    }

    override fun requestHomeChannelData(
        page: Int,
        tid: String,
        callback: (t: ArrayList<HomeChannelData>?) -> Unit
    ) {
        OkGo.get<String>("$baseUrl?ac=videolist&t=$tid&pg=$page")
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    callback.invoke(parseHomeChannelData1(response?.body()))
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    callback.invoke(null)
                }
            })
    }


    override fun <T> requestSearchData(searchName: String, callback: (t: T?) -> Unit) {
    }

    override fun <T> requestDetailData(id: String, callback: (t: T?) -> Unit) {
    }

}