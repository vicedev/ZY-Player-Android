package com.vicedev.zy_player_android.net

import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.ui.detail.model.FilmDetailModel
import com.vicedev.zy_player_android.ui.detail.model.FilmItemInfo
import com.vicedev.zy_player_android.ui.home.model.FilmModel
import com.vicedev.zy_player_android.ui.home.model.FilmModelItem
import org.jsoup.Jsoup

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 0:20
 */
object DataParser {
    /**
     * 最新数据解析
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
            val filmModelItemList = ArrayList<FilmModelItem>()
            val doc = Jsoup.parse(data)
            val elements = doc.select(".xing_vb li")
            for (i in 1 until elements.size - 1) {
                val element = elements[i]
                val name = element.child(1).text()
                val type = element.child(2).text()
                val time = element.child(3).text()
                val detail = ConfigManager.configMap[key]?.url + element.child(1).selectFirst("a")
                    .attr("href")
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
        try {
            val filmModelItemList = ArrayList<FilmModelItem>()
            val doc = Jsoup.parse(data)
            val elements = doc.select(".videoContent li")
            for (element in elements) {
                val name = element.selectFirst(".videoName").text()
                val type = element.selectFirst(".category").text()
                val time = element.selectFirst(".time").text()
                val detail =
                    ConfigManager.configMap[key]?.url + element.select(".address").attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val update = doc.selectFirst(".header_list li span").text().toInt()
            val select = doc.select(".pagination li")
            val total = select[select.size - 2].text().toInt()
            return FilmModel(filmModelItemList, update, total)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseFilmGetTypeTwo(key: String, data: String): FilmModel? {
        try {
            val filmModelItemList = ArrayList<FilmModelItem>()
            val doc = Jsoup.parse(data)
            val elements = doc.select(".nr")
            println(elements.size)
            for (element in elements) {
                val name = element.selectFirst(".name").text()
                val type = element.selectFirst(".btn_span").text()
                val time = element.selectFirst(".hours").text()
                val detail =
                    ConfigManager.configMap[key]?.url + element.select(".name").attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val update = doc.selectFirst(".kfs em").text().toInt()
            val t =
                doc.selectFirst(".pag2").text().split("条").toTypedArray()[0].split("共")
                    .toTypedArray()[1]
            val total = t.toInt()
            return FilmModel(filmModelItemList, update, total)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseFilmGetTypeThree(key: String, data: String): FilmModel? {
        try {
            val filmModelItemList = ArrayList<FilmModelItem>()
            val doc = Jsoup.parse(data)
            val elements = doc.select(".xing_vb li")
            for (i in 1 until elements.size - 1) {
                val element = elements[i]
                val name = element.child(1).text()
                val type = element.child(2).text()
                val time = element.child(3).text()
                val detail = ConfigManager.configMap[key]?.url + element.child(1).selectFirst("a")
                    .attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val update = doc.select(".xing_top_right li strong")[0].text().toInt()
            val total =
                doc.select(".pages")[0].text().split("条").toTypedArray()[0].split("共")
                    .toTypedArray()[1].toInt()
            return FilmModel(filmModelItemList, update, total)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * 搜索结果解析
     */
    fun parseSearchGet(key: String, data: String, type: Int): FilmModel? {
        return when (type) {
            0 -> searchGetTypeZro(key, data)
            1 -> searchGetTypeOne(key, data)
            3 -> searchGetTypeThree(key, data)
            else -> null
        }
    }

    private fun searchGetTypeZro(key: String, data: String): FilmModel? {
        try {
            val filmModelItemList = ArrayList<FilmModelItem>();
            val doc = Jsoup.parse(data)
            val elements = doc.select(".xing_vb li")
            for (i in 1 until elements.size - 1) {
                val element = elements[i]
                val name = element.child(1).text()
                val type = element.child(2).text()
                val time = element.child(3).text()
                val detail = ConfigManager.configMap[key]?.url + element.child(1).selectFirst("a")
                    .attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val total = doc.select(".nvc dd").text().replace("[^0-9]".toRegex(), "").toInt()
            return FilmModel(filmModelItemList, -1, total)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun searchGetTypeOne(key: String, data: String): FilmModel? {
        try {
            val filmModelItemList = ArrayList<FilmModelItem>();
            val doc = Jsoup.parse(data)
            val elements = doc.select(".videoContent li")
            for (element in elements) {
                val name = element.selectFirst(".videoName").text()
                val type = element.selectFirst(".category").text()
                val time = element.selectFirst(".time").text()
                val detail =
                    ConfigManager.configMap[key]?.url + element.selectFirst(".address").attr("href")
                filmModelItemList.add(FilmModelItem(key, name, type, time, detail))
            }
            val total = elements.size
            return FilmModel(filmModelItemList, -1, total)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun searchGetTypeThree(key: String, data: String): FilmModel? {
        return null
    }


    /**
     * 详情解析
     */
    fun parseDetailGet(key: String, data: String, type: Int): FilmDetailModel? {
        return when (type) {
            0 -> detailGetTypeZro(key, data)
            1 -> null
            2 -> null
            3 -> null
            else -> null
        }
    }


    private fun detailGetTypeZro(key: String, data: String): FilmDetailModel? {
        try {
            val doc = Jsoup.parse(data)
            val vodBox = doc.select(".vodBox")
            val info = vodBox.text()
            val title = doc.select(".vodh h2").text()
            val index = doc.select(".vodh span").text()
            val name = title + index
            val vodInfo = doc.select(".playBox")
            var desc = ""
            for (element in vodInfo) {
                val k = element.text()
                if (k.contains("剧情介绍")) {
                    desc = element.select(".vodplayinfo").text()
                    break
                }
            }
            val vodLi = doc.select(".ibox .vodplayinfo li")
            val m3u8List = ArrayList<FilmItemInfo>()
            val mp4List = ArrayList<FilmItemInfo>()
            for (element in vodLi) {
                val text = element.text()
                if (text.contains(".m3u8")) {
                    val splitm3u8 = text.split("$")
                    m3u8List.add(FilmItemInfo(splitm3u8[0], splitm3u8[1]))
                } else if (text.contains(".mp4")) {
                    val splitmp4 = text.split("$")
                    mp4List.add(FilmItemInfo(splitmp4[0], splitmp4[1]))
                }
            }
            return FilmDetailModel(key, title, desc, m3u8List, mp4List)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}