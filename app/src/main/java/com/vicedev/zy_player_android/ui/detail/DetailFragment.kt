package com.vicedev.zy_player_android.ui.detail

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.*
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.DetailData
import com.vicedev.zy_player_android.sources.bean.Video
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.detail.controller.VideoController
import com.vicedev.zy_player_android.ui.detail.controller.WebController
import com.vicedev.zy_player_android.utils.Utils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/8 21:48
 * @desc 详情页
 */

const val SOURCE_KEY = "source_key"
const val ID = "id"

class DetailFragment : BaseFragment() {

    private var source: BaseSource? = null
    private lateinit var id: String
    private var playVideo: Video? = null
    private var detailData: DetailData? = null
    private var playVideoList: ArrayList<Video>? = null
    private var curPlayPos = 0

    private var videoController: VideoController? = null
    private var webController: WebController? = null

    private var anthologyList: BottomPopupView? = null

    companion object {
        fun instance(sourceKey: String, id: String): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(SOURCE_KEY to sourceKey, ID to id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = ConfigManager.generateSource(ConfigManager.OKZYW)
        id = arguments?.getString(ID).textOrDefault()
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_LEFT_BUTTON -> {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        statusView.failRetryClickListener = {
            initData()
        }
    }

    override fun initListener() {
        super.initListener()
        //网页播放
        ivWebPlay.setOnClickListener {
            if (playVideo == null || playVideo?.playUrl.isNullOrBlank()) {
                ToastUtils.showShort("无法播放")
                return@setOnClickListener
            }
            Utils.openBrowser(
                requireActivity(),
                if (playVideo?.playUrl.canPlayInAppUrl()) {
                    "http://zyplayer.fun/player/player.html?url=${playVideo?.playUrl?.textOrDefault()}&title=${playVideo?.name}"
                } else {
                    playVideo?.playUrl.textOrDefault()
                }
            )
        }
        //选集
        llAnthology.setOnClickListener {
            if (playVideoList?.size ?: 0 > 1) {
                if (anthologyList == null) {
                    anthologyList = XPopup.Builder(requireActivity())
                        .asBottomList("选集",
                            playVideoList?.map { it.name }?.toTypedArray(),
                            null,
                            0,
                            OnSelectListener { position, text ->
                                curPlayPos = position
                                playVideo(playVideoList?.get(position))
                            })
                }
                anthologyList?.show()
            }
        }
    }


    override fun onPause() {
        webController?.onPause()
        videoController?.onPause()
        super.onPause()
    }

    override fun onResume() {
        webController?.onResume()
        videoController?.onResume()
        super.onResume()
    }

    override fun onDestroyView() {
        webController?.onDestroy()
        videoController?.onDestroy()
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean {
        return videoController?.onBackPressed() ?: false || webController?.onBackPressed() ?: false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        videoController?.onConfigurationChanged(newConfig)
    }

    override fun initData() {
        super.initData()
        statusView.setLoadingStatus()
        source?.requestDetailData(id) {
            when {
                it == null -> {
                    statusView.setFailStatus()
                }
                it.videoList.isNullOrEmpty() -> {
                    statusView.setEmptyStatus()
                }
                else -> {
                    statusView.setSuccessStatus()
                    setData(it)
                }
            }
        }
    }

    private fun setData(detailData: DetailData) {
        this.detailData = detailData
        detailData.run {
            playVideoList = videoList

            //是否支持选集
            if (playVideoList?.size ?: 0 > 1) {
                ivPlayMore.visible()
            } else {
                ivPlayMore.gone()
            }

            playVideo(detailData.videoList?.get(0))
            //名字
            tvName.text = name
            titleBar?.centerTextView?.text = name
            //简介
            tvDesc.text = des.textOrDefault()
        }
    }

    private fun playVideo(playVideo: Video?) {
        if (playVideo == null) return
        this.playVideo = playVideo
        if (playVideo.playUrl.canPlayInAppUrl()) {
            //初始化视频控制
            if (videoController == null) {
                videoController = VideoController()
                videoController?.init(requireActivity(), videoPlayer)
            }
            videoController?.play(
                playVideo.playUrl,
                "${detailData?.name.textOrDefault()}   ${playVideo.name.textOrDefault()}"
            )
            videoPlayer.visible()
            flWebView.gone()
        } else {
            //网页播放
            if (webController == null) {
                webController = WebController()
            }
            webController?.loadUrl(this@DetailFragment, playVideo.playUrl, flWebView)
            videoPlayer.gone()
            flWebView.visible()
        }
        //正在播放
        tvCurPlayName.text = playVideo?.name.textOrDefault()
    }

}