package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.util.AttributeSet
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.visible
import kotlinx.android.synthetic.main.custom_video_layout.view.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/18 12:47
 * @desc 自定义GSYVideoPlayer
 */

class CustomGSYVideoPlayer : StandardGSYVideoPlayer {

    constructor (context: Context?) : super(context)
    constructor (context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor (context: Context?, attrs: AttributeSet?) : super(context, attrs)

    companion object {
        var speedText = "1x"
        var scaleTypeText = "默认比例"

        fun reset() {
            speedText = "1x"
            scaleTypeText = "默认比例"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT)
        }
    }

    private val scaleTypes = arrayListOf<ScaleType>(
        ScaleType(GSYVideoType.SCREEN_TYPE_DEFAULT, "默认比例"),
        ScaleType(GSYVideoType.SCREEN_TYPE_16_9, "16:9"),
        ScaleType(GSYVideoType.SCREEN_TYPE_4_3, "4:3"),
        ScaleType(GSYVideoType.SCREEN_TYPE_FULL, "全屏"),
        ScaleType(GSYVideoType.SCREEN_MATCH_FULL, "拉伸全屏")
    )

    private val speeds = arrayListOf<Speed>(
        Speed(5.0f, "5x"),
        Speed(4.0f, "4x"),
        Speed(3.0f, "3x"),
        Speed(2.0f, "2x"),
        Speed(1.75f, "1.75x"),
        Speed(1.5f, "1.5x"),
        Speed(1.25f, "1.25x"),
        Speed(1.0f, "1x"),
        Speed(0.75f, "0.75x"),
        Speed(0.5f, "0.5x")
    )

    override fun getLayoutId(): Int {
        return R.layout.custom_video_layout
    }

    init {

        tvSpeed.text = speedText
        tvScaleType.text = scaleTypeText

        //切屏幕比例
        tvScaleType.setOnClickListener {
            if (!mHadPlay) return@setOnClickListener
            showScaleSelectPanel()
        }

        //倍速播放
        tvSpeed.setOnClickListener {
            if (!mHadPlay) return@setOnClickListener
            showSpeedSelectPanel()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //全屏才展示自定义按钮
        if (mIfCurrentIsFullscreen) {
            llCustomBtn.visible()
        } else {
            llCustomBtn.gone()
        }
    }

    private fun showScaleSelectPanel() {
        hideBottomLayout()
        videoSelectPanel.setOnItemClickListener { s, pos ->
            val scaleType = scaleTypes[pos]
            tvScaleType.text = scaleType.name
            GSYVideoType.setShowType(scaleType.type)

            scaleTypeText = scaleType.name

            changeTextureViewShowType()
            mTextureView?.requestLayout()
        }
        videoSelectPanel.setData(scaleTypes.map { it.name } as ArrayList<String>)
    }

    private fun showSpeedSelectPanel() {
        hideBottomLayout()
        videoSelectPanel.setOnItemClickListener { s, pos ->
            val speedData = speeds[pos]
            setSpeed(speedData.speed, true)
            tvSpeed.text = speedData.name
            speedText = speedData.name
        }
        videoSelectPanel.setData(speeds.map { it.name } as ArrayList<String>)
    }

    private fun hideBottomLayout() {
        val time = mDismissControlTime
        mDismissControlTime = 0
        startDismissControlViewTimer()
        mDismissControlTime = time
    }

    inner class ScaleType constructor(val type: Int, val name: String)
    inner class Speed constructor(val speed: Float, val name: String)
}