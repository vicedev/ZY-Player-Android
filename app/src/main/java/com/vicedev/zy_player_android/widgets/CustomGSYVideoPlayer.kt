package com.vicedev.zy_player_android.widgets

import android.content.Context
import android.util.AttributeSet
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.vicedev.zy_player_android.R
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

    private var curScaleTypePos = 0

    private val scaleTypes = arrayListOf<ScaleType>(
        ScaleType(GSYVideoType.SCREEN_TYPE_DEFAULT, "默认比例"),
        ScaleType(GSYVideoType.SCREEN_TYPE_16_9, "16:9"),
        ScaleType(GSYVideoType.SCREEN_TYPE_4_3, "4:3"),
        ScaleType(GSYVideoType.SCREEN_TYPE_FULL, "全屏"),
        ScaleType(GSYVideoType.SCREEN_MATCH_FULL, "拉伸全屏")
    )

    override fun getLayoutId(): Int {
        return R.layout.custom_video_layout
    }

    init {
        //切屏幕比例
        tvScaleType.setOnClickListener {
            curScaleTypePos++
            if (curScaleTypePos > scaleTypes.size - 1) {
                curScaleTypePos = 0
            }
            val scaleType = scaleTypes[curScaleTypePos]
            tvScaleType.text = scaleType.name
            GSYVideoType.setShowType(scaleType.type)

            changeTextureViewShowType()
            mTextureView?.requestLayout()
        }
    }

    inner class ScaleType constructor(val type: Int, val name: String)
}