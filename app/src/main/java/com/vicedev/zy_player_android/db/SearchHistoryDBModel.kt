package com.vicedev.zy_player_android.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport
import java.util.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/12 20:46
 * @desc 搜索历史的数据库类
 */
class SearchHistoryDBModel : LitePalSupport() {
    @Column(unique = true, defaultValue = "")
    var searchWord: String? = null
    var updateData: Date? = null
}