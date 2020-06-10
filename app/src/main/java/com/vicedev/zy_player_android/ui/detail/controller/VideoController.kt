package com.vicedev.zy_player_android.ui.detail.controller

import android.app.Activity
import android.content.res.Configuration
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.ui.detail.model.FilmDetailModel

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/10 12:40
 */
class VideoController {
    private lateinit var videoPlayer: StandardGSYVideoPlayer
    private lateinit var filmDetail: FilmDetailModel
    private lateinit var activity: Activity

    private var isPlay = false
    private var isPause = false

    //外部辅助的旋转，帮助全屏
    private var orientationUtils: OrientationUtils? = null

    fun init(activity: Activity, videoPlayer: StandardGSYVideoPlayer, filmDetail: FilmDetailModel) {
        this.videoPlayer = videoPlayer
        this.filmDetail = filmDetail
        this.activity = activity

        initVideoPlayer()
    }

    private fun initVideoPlayer() {
        //增加封面
//        val imageView = ImageView(this)
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
//        imageView.setImageResource(R.mipmap.xxx1)
//        videoPlayer.thumbImageView = imageView
        //增加title
        videoPlayer.titleTextView.gone()
        //设置返回键
        videoPlayer.backButton.gone()
        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(activity, videoPlayer).apply {
            //初始化不打开外部的旋转
            isEnable = true
        }

        val m3u8List = filmDetail.m3u8List
        if (m3u8List.isNullOrEmpty()) {
            return
        }

        GSYVideoOptionBuilder()
//            .setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(m3u8List[0])
            .setCacheWithPlay(false)
            .setVideoTitle(filmDetail.title)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils?.backToProtVideo()
                }
            })
            .setLockClickListener { view, lock ->
                orientationUtils?.isEnable = !lock
            }.build(videoPlayer)

        videoPlayer.fullscreenButton
            .setOnClickListener { //直接横屏
                orientationUtils?.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayer.startWindowFullscreen(activity, true, true)
            }

        videoPlayer.startPlayLogic()
    }

    fun onPause() {
        videoPlayer.currentPlayer.onVideoPause()
        isPause = true
    }

    fun onResume() {
        videoPlayer.currentPlayer.onVideoResume(false)
        isPause = false
    }

    fun onDestroy() {
        if (isPlay) {
            videoPlayer.currentPlayer.release()
        }
        orientationUtils?.releaseListener()
    }

    fun onBackPressed(): Boolean {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(activity)) {
            return true
        }
        return false
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayer.onConfigurationChanged(activity, newConfig, orientationUtils, true, true)
        }
    }
}