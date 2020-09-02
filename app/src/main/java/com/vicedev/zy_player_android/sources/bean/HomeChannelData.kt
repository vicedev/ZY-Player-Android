package com.vicedev.zy_player_android.sources.bean

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 23:51
 * @desc 首页频道数据
 */
data class HomeChannelData(
    //更新时间
    val updateTime: String?,
    //视频id
    val id: String?,
    //分类id
    val tid: String?,
    //名字
    val name: String?,
    //视频类型，国产剧
    val type: String?,
    //图片
    val pic: String?,
    //地区，大陆
    val area: String?,
    //年份，2017
    val year: String?,
    //演员
    val actor: String?
)