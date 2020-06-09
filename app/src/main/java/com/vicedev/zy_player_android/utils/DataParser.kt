package com.vicedev.zy_player_android.utils

import com.vicedev.zy_player_android.ui.home.model.FilmModel
import com.vicedev.zy_player_android.ui.home.model.FilmModelItem
import org.jsoup.Jsoup

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 0:20
 */
object DataParser {
    /**
     * 最新数据
     */
    fun parseFilmGet(key: String, data: String, type: Int): FilmModel? {
        return when (type) {
            0 -> parseFilmGetTypeZro(key, data)
            1 -> parseFilmGetTypeOne(key, data)
            2 -> parseFilmGetTypeTwo(key, data)
            3 -> parseFilmGetTypeThree(key, data)
            else -> null
        }
    }

    private fun parseFilmGetTypeZro(key: String, data: String): FilmModel? {
        try {
            val filmModelItemList = ArrayList<FilmModelItem>();
            val doc = Jsoup.parse(data)
            val elements = doc.select(".xing_vb li")
            for (i in 1 until elements.size - 1) {
                val element = elements[i]
                val name = element.child(1).text()
                val type = element.child(2).text()
                val time = element.child(3).text()
                val detail = key + element.child(1).selectFirst("a").attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val update = doc.select(".xing_top_right li strong")[0].text().toInt()
            val total = doc.select(".pages")[0].text().split("条").toTypedArray()[0].split("共")
                .toTypedArray()[1].toInt()
            return FilmModel(filmModelItemList, update, total)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseFilmGetTypeOne(key: String, data: String): FilmModel? {
        return null
    }

    private fun parseFilmGetTypeTwo(key: String, data: String): FilmModel? {
        return null
    }

    private fun parseFilmGetTypeThree(key: String, data: String): FilmModel? {
        return null
    }
}