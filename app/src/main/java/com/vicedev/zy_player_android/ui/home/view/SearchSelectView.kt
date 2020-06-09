package com.vicedev.zy_player_android.ui.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.*
import kotlinx.android.synthetic.main.search_select_view.view.*

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 22:10
 */

class SearchSelectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onSelectListener: ((key: String, id: Int) -> Unit)? = null

    init {
        View.inflate(context, R.layout.search_select_view, this)
    }

    fun initData() {
        setSpinnerData()
    }

    private fun setSpinnerData() {
        val config = ConfigManager.config
        setSpinner1Data(config)
    }

    private fun setSpinner1Data(config: Config) {
        spinner1.attachDataSource(config.map { it.name })
        spinner1.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            setSpinner2Data(config[position])
        }
        setSpinner2Data(config[0])
    }

    private fun setSpinner2Data(configItem: ConfigItem) {
        val list = configItem.tags.map { it.title }
        spinner2.attachDataSource(list)
        spinner2.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            setSpinner3Data(configItem, configItem.tags[position])
        }
        setSpinner3Data(configItem, configItem.tags[0])
    }

    private fun setSpinner3Data(configItem: ConfigItem, tag: Tag) {
        val children = tag.children.toMutableList()
        children.add(
            0,
            Children(tag.id, "全部")
        )
        val list = children.map { it.title }
        spinner3.attachDataSource(list)
        spinner3.setOnSpinnerItemSelectedListener { parent, view, position, id ->
//            LogUtils.d(jointUrl(configItem, children[position].id))
//            NetLoader.filmGet(configItem.key, children[position].id)
            onSelectListener?.invoke(configItem.key, children[position].id)
        }
        if (list.size == 1) {
            spinner3.invisible()
        } else {
            spinner3.visible()
        }
//        LogUtils.d(jointUrl(configItem, children[0].id))
//        NetLoader.filmGet(configItem.key, children[0].id)
        onSelectListener?.invoke(configItem.key, children[0].id)
    }

    private fun jointUrl(configItem: ConfigItem, id: Int): String {
        return if (id == 0) {
            configItem.new.replace("{page}", "1")
        } else {
            configItem.view.replace("{id}", id.toString()).replace("{page}", "1")
        }
    }
}