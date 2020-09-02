package com.vicedev.zy_player_android.sources

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.vicedev.zy_player_android.sources.bean.HomeData

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 21:47
 * @desc OK 资源网
 */
class OKZYWSource : BaseSource() {
    init {
        baseUrl = "http://cj.okzy.tv/inc/api.php";
        downloadBaseUrl = "http://cj.okzy.tv/inc/apidown.php"
    }

    override fun requestHomeData(callback: (t: HomeData?) -> Unit) {
        OkGo.get<String>(baseUrl)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    callback.invoke(parseHomeData1(response?.body()))
                }
            })
    }

    override fun <T> requestSearchData(searchName: String, callback: (t: T?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun <T> requestDetailData(id: String, callback: (t: T?) -> Unit) {
        TODO("Not yet implemented")
    }

}