package com.vicedev.zy_player_android.ui.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.DetailData
import com.vicedev.zy_player_android.sources.bean.Video
import com.vicedev.zy_player_android.ui.BaseFragment
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
        detailData.run {
            playVideo = detailData.videoList?.get(0)
            //名字
            tvName.text = name
            //简介
            tvDesc.text = des.textOrDefault()
            //正在播放
            tvCurPlayName.text=playVideo?.name.textOrDefault()
            //图
            Glide.with(requireActivity())
                .load(pic)
                .into(ivPic)
        }
    }
}