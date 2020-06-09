package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.invisible
import com.vicedev.zy_player_android.common.visible
import kotlinx.android.synthetic.main.status_view_layout.view.*

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 17:10
 */
class StatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var failRetryClickListener: (() -> Unit)? = null

    init {

        View.inflate(context, R.layout.status_view_layout, this)

        setOnClickListener {
            if (statusFailView.isVisible) {
                failRetryClickListener?.invoke()
            }
        }

        gone()
    }

    fun setLoadingStatus() {
        visible()
        statusLoadingView.visible()
        statusFailView.invisible()
        statusEmptyView.invisible()
    }

    fun setFailStatus() {
        visible()
        statusLoadingView.invisible()
        statusFailView.visible()
        statusEmptyView.invisible()
    }

    fun setEmptyStatus() {
        visible()
        statusLoadingView.invisible()
        statusFailView.invisible()
        statusEmptyView.visible()
    }

    fun setSuccessStatus() {
        gone()
    }
}