package com.vicedev.zy_player_android.sources.bean

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/8 22:28
 * @desc 详情页数据
 */

data class DetailData(
    //视频id
    val id: String?,
    //分类id
    val tid: String?,
    //名字
    val name: String?,
    //类型
    val type: String?,
    //语言
    val lang: String?,
    //地区
    val area: String?,
    //图片
    val pic: String?,
    //上映年份
    val year: String?,
    //主演
    val actor: String?,
    //导演
    val director: String?,
    //简介
    val des:String?,
    //播放列表
    val videoList: ArrayList<Video>?,
    //所属视频源的key
    val sourceKey: String?
)

data class Video(
    val name: String?,
    val playUrl: String?
)