package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.common.visible
import kotlinx.android.synthetic.main.set_item_view.view.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/17 17:33
 * @desc 设置的单条目
 */
class SetItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var itemName: String? = null
        set(value) {
            field = value
            tvName?.text = value.textOrDefault()
        }

    init {
        View.inflate(context, R.layout.set_item_view, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.SetItemView)
        tvName.text = ta.getString(R.styleable.SetItemView_itemName)
        if (ta.getBoolean(R.styleable.SetItemView_needDivideLine, true)) {
            divideLine.visible()
        } else {
            divideLine.gone()
        }
        if (ta.getBoolean(R.styleable.SetItemView_needRightArrow, true)) {
            ivArrowRight.visible()
        } else {
            ivArrowRight.gone()
        }
        ta.recycle()
    }

}