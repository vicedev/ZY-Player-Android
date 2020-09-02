package com.vicedev.zy_player_android.sources

import com.vicedev.zy_player_android.sources.bean.Classify
import com.vicedev.zy_player_android.sources.bean.HomeData
import com.vicedev.zy_player_android.sources.bean.NewVideo
import com.vicedev.zy_player_android.utils.Utils

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 21:17
 */

abstract class BaseSource {

    var baseUrl: String? = null
    var downloadBaseUrl: String? = null

    //请求首页数据
    abstract fun requestHomeData(callback: (t: HomeData?) -> Unit)

    //请求搜索数据
    abstract fun <T> requestSearchData(searchName: String, callback: (t: T?) -> Unit)

    //请求详情数据
    abstract fun <T> requestDetailData(id: String, callback: (t: T?) -> Unit)


    //以下为解析数据
    fun parseHomeData1(data: String?): HomeData? {
        try {
            if (data == null) return null
            val jsonObject = Utils.xmlToJson(data)?.toJson()
            jsonObject?.getJSONObject("rss")?.run {
                val videoList = ArrayList<NewVideo>()
                val videos = getJSONObject("list").getJSONArray("video")
                try {
                    for (i in 0 until videos.length()) {
                        val json = videos.getJSONObject(i)
                        videoList.add(
                            NewVideo(
                                json.getString("last"),
                                json.getString("id"),
                                json.getString("tid"),
                                json.getString("name"),
                                json.getString("type")
                            )
                        )
                    }
                } catch (e: Exception) {
                }
                val classifyList = ArrayList<Classify>()
                try {
                    val classList = getJSONObject("class").getJSONArray("ty")
                    for (i in 0 until classList.length()) {
                        val json = classList.getJSONObject(i)
                        classifyList.add(
                            Classify(
                                json.getString("id"),
                                json.getString("content")
                            )
                        )
                    }
                } catch (e: Exception) {
                }
                return HomeData(videoList, classifyList)
            }
        } catch (e: Exception) {
        }
        return null
    }
}