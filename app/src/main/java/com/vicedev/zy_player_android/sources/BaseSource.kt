package com.vicedev.zy_player_android.sources

import com.lzy.okgo.OkGo
import com.vicedev.zy_player_android.common.canPlayInAppUrl
import com.vicedev.zy_player_android.sources.bean.*
import com.vicedev.zy_player_android.utils.Utils
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 21:17
 */

abstract class BaseSource {
    abstract val key: String
    abstract val name: String
    abstract val baseUrl: String
    abstract val downloadBaseUrl: String

    //请求首页数据
    abstract fun requestHomeData(callback: (t: HomeData?) -> Unit)

    //请求频道列表数据
    abstract fun requestHomeChannelData(
        page: Int,
        tid: String,
        callback: (t: ArrayList<HomeChannelData>?) -> Unit
    )

    //请求搜索数据
    abstract fun requestSearchData(
        searchWord: String,
        page: Int,
        callback: (t: ArrayList<SearchResultData>?) -> Unit
    )

    //请求详情数据
    abstract fun requestDetailData(id: String, callback: (t: DetailData?) -> Unit)


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

    fun parseHomeChannelData1(data: String?): ArrayList<HomeChannelData>? {
        try {
            if (data == null) return null
            val jsonObject = Utils.xmlToJson(data)?.toJson()
            val videoList = ArrayList<HomeChannelData>()
            val videos =
                jsonObject?.getJSONObject("rss")?.getJSONObject("list")!!.getJSONArray("video")
            for (i in 0 until videos.length()) {
                val json = videos.getJSONObject(i)
                videoList.add(
                    HomeChannelData(
                        json.getString("id"),
                        json.getString("name"),
                        json.getString("pic")
                    )
                )
            }
            return videoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return arrayListOf()
    }

    fun parseSearchResultData1(data: String?): ArrayList<SearchResultData>? {
        try {
            if (data == null) return null
            val jsonObject = Utils.xmlToJson(data)?.toJson()
            val videoList = ArrayList<SearchResultData>()
            val videos =
                jsonObject?.getJSONObject("rss")?.getJSONObject("list")!!.getJSONArray("video")
            for (i in 0 until videos.length()) {
                val json = videos.getJSONObject(i)
                videoList.add(
                    SearchResultData(
                        json.getString("id"),
                        json.getString("name"),
                        json.getString("type")
                    )
                )
            }
            return videoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return arrayListOf()
    }

    fun parseDetailData1(sourceKey: String, data: String?): DetailData? {
        try {
            if (data == null) return null
            val jsonObject = Utils.xmlToJson(data)?.toJson()
            val videoInfo =
                jsonObject?.getJSONObject("rss")?.getJSONObject("list")!!.getJSONObject("video")
            val dd = videoInfo.getJSONObject("dl").get("dd")
            var videoList: ArrayList<Video>? = null
            if (dd is JSONObject) {
                videoList = dd.getString(("content"))?.split("#")
                    ?.map {
                        val split = it.split("$")
                        if (split.size == 2) {
                            Video(split[0], split[1])
                        } else {
                            if (split[0].startsWith("http")) {
                                Video(split[0], split[0])
                            } else {
                                Video(split[0], split[0])
                            }
                        }
                    }?.toMutableList() as ArrayList<Video>? ?: arrayListOf()
            } else if (dd is JSONArray) {
                for (i in 0 until dd.length()) {
                    val list = dd.getJSONObject(i)?.getString("content")?.split("#")
                        ?.map {
                            val split = it.split("$")
                            Video(split[0], split[1])
                        }?.toMutableList() as ArrayList<Video>? ?: arrayListOf()
                    if (list.size > 0) {
                        videoList = list
                        if (list[0].playUrl.canPlayInAppUrl()) {
                            //优先获取应用内播放的资源
                            break
                        }
                    }
                }
            }
            return DetailData(
                videoInfo.getString("id"),
                videoInfo.getString("tid"),
                videoInfo.getString("name"),
                videoInfo.getString("type"),
                videoInfo.getString("lang"),
                videoInfo.getString("area"),
                videoInfo.getString("pic"),
                videoInfo.getString("year"),
                videoInfo.getString("actor"),
                videoInfo.getString("director"),
                videoInfo.getString("des"),
                videoList,
                sourceKey
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun cancelAll() {
        OkGo.cancelTag(OkGo.getInstance().okHttpClient, key)
    }
}