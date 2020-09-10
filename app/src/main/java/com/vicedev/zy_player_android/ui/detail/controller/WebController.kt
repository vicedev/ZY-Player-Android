package com.vicedev.zy_player_android.ui.detail.controller

import android.app.Activity
import android.graphics.Color
import android.icu.text.UFormat
import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_detail.*

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
            .useDefaultIndicator()
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

}



