package com.vicedev.zy_player_android.ui.detail

import android.content.res.Configuration
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.detail.controller.VideoController
import com.vicedev.zy_player_android.ui.detail.model.FilmDetailModel
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_film_detail.*


/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/9 22:49
 */
class FilmDetailFragment : BaseFragment() {
    private val videoController by lazy { VideoController() }

    override fun getLayoutId(): Int = R.layout.fragment_film_detail

    override fun initTitleBar(titleBar: CommonTitleBar?) {
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        videoController.init(
            requireActivity(), videoPlayer, FilmDetailModel(
                "",
                "ssss",
                "ssssss",
                arrayListOf("https://hong.tianzhen-zuida.com/20200224/20757_50596525/index.m3u8"),
                arrayListOf()
            )
        )
    }


    override fun onPause() {
        super.onPause()
        videoController.onPause()
    }

    override fun onResume() {
        super.onResume()
        videoController.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoController.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return videoController.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        videoController.onConfigurationChanged(newConfig)
    }

}