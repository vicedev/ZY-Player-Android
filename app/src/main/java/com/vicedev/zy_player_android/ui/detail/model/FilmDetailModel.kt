package com.vicedev.zy_player_android.ui.detail.model

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/10 10:47
 */

data class FilmDetailModel(
    val site: String,
    val title: String,
    val desc: String,
    val m3u8List: ArrayList<String>,
    val mp4List: ArrayList<String>
)