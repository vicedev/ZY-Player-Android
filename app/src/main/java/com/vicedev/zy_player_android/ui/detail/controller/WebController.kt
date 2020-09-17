package com.vicedev.zy_player_android.ui.detail.controller

import android.content.Context
import android.graphics.Color
import android.net.http.SslError
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.just.agentweb.AgentWeb
import com.just.agentweb.BaseIndicatorView
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.visible
import kotlinx.android.synthetic.main.custom_web_indicator.view.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/10 23:38
 * @desc 网页控制器
 */

class WebController {
    private var agentWeb: AgentWeb? = null
    fun loadUrl(fragment: Fragment, url: String?, container: ViewGroup) {
        agentWeb = AgentWeb.with(fragment)
            .setAgentWebParent(
                container,
                ViewGroup.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
            .setCustomIndicator(CustomIndicator(container.context))
            .setWebViewClient(getWebViewClient())
            .createAgentWeb()
            .ready()
            .go(url)
            .apply {
                webCreator.webView.setBackgroundColor(Color.BLACK)
            }
    }

    fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
    }

    fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
    }

    fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
    }

    fun onBackPressed(): Boolean {
        return agentWeb?.back() == true
    }


    private fun getWebViewClient(): com.just.agentweb.WebViewClient {
        return object : com.just.agentweb.WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                handler.proceed()
            }
        }
    }

    inner class CustomIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : BaseIndicatorView(context, attrs, defStyleAttr) {

        init {
            View.inflate(context, R.layout.custom_web_indicator, this)
        }

        override fun offerLayoutParams(): LayoutParams {
            return FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        override fun show() {
            super.show()
            visible()
        }

        override fun hide() {
            super.hide()
            gone()
        }

        override fun setProgress(newProgress: Int) {
            super.setProgress(newProgress)
            tvProgress.text = "$newProgress%"
        }

        override fun reset() {
            super.reset()
            tvProgress.text = "加载中..."
        }
    }
}



