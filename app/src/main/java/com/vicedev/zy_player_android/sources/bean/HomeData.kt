package com.vicedev.zy_player_android.sources.bean

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 21:23
 */

data class HomeData(val videoList: ArrayList<NewVideo>, val classifyList: ArrayList<Classify>)

//新更新的资源
data class NewVideo(
    //更新时间
    val updateTime: String?,
    //视频id
    val id: String?,
    //分类id
    val tid: String?,
    //名字
    val name: String?,
    //视频类型，国产剧
    val type: String?
)

//分类
data class Classify(
    //分类id
    val id: String?,
    //分类名
    val name: String?
)