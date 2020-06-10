package com.vicedev.zy_player_android.ui.detail

import android.content.res.Configuration
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.CommonCallback
import com.vicedev.zy_player_android.net.NetLoader
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.detail.controller.VideoController
import com.vicedev.zy_player_android.ui.detail.model.FilmDetailModel
import com.vicedev.zy_player_android.ui.detail.model.FilmItemInfo
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_film_detail.*


/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 22:49
 */

class FilmDetailFragment : BaseFragment() {
    private var videoController: VideoController? = null

    private val key by lazy { activity?.intent?.getStringExtra(KEY) }
    private val detailUrl by lazy { activity?.intent?.getStringExtra(DETAIL_URL) }

    private var m3u8List: ArrayList<FilmItemInfo>? = null
    private var mp4List: ArrayList<FilmItemInfo>? = null

    companion object {
        const val KEY = "key"
        const val DETAIL_URL = "detail_url"
    }

    override fun getLayoutId(): Int = R.layout.fragment_film_detail

    override fun initTitleBar(titleBar: CommonTitleBar?) {
    }

    override fun initView() {
        super.initView()
        statusView.failRetryClickListener = {
            initData()
        }
    }

    override fun initListener() {
        super.initListener()
        //选集
        spinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            videoController?.play(m3u8List?.get(position))
        }
    }

    override fun initData() {
        super.initData()

        if (key.isNullOrBlank() || detailUrl.isNullOrBlank()) {
            statusView.setFailStatus()
            return
        }

        statusView.setLoadingStatus()

        NetLoader.filmDetailGet(key!!, detailUrl!!, object : CommonCallback<FilmDetailModel?> {
            override fun onResult(t: FilmDetailModel?) {
                t?.let {
                    setData(t)
                    statusView.setSuccessStatus()
                } ?: let {
                    statusView.setFailStatus()
                }
            }
        })
    }

    private fun setData(t: FilmDetailModel) {
        //视频
        m3u8List = t.m3u8List
        mp4List = t.mp4List

        if (m3u8List.isNullOrEmpty()) {
            return
        }

        if (videoController == null) {
            videoController = VideoController()
        }
        videoController?.run {
            init(requireActivity(), videoPlayer)
            //进来播放第一个
            play(m3u8List!![0])
        }

        //选集
        spinner.attachDataSource(m3u8List!!.map { it.name })

        //底下数据
        tvName.text = t.title
        tvDesc.text = t.desc
    }


    override fun onPause() {
        super.onPause()
        videoController?.onPause()
    }

    override fun onResume() {
        super.onResume()
        videoController?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoController?.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return videoController?.onBackPressed() ?: false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        videoController?.onConfigurationChanged(newConfig)
    }

}