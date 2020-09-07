package com.vicedev.zy_player_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.common.gone
import com.vicedev.zy_player_android.common.textOrDefault
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.Classify
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.channel.HomeChannelFragment
import com.vicedev.zy_player_android.ui.search.SearchActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_home.statusView
import kotlinx.android.synthetic.main.fragment_home_new.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/2 22:39
 */
class NewHomeFragment : BaseFragment() {
    private var source: BaseSource? = null

    private var classifyList: ArrayList<Classify>? = null

    override fun getLayoutId(): Int = R.layout.fragment_home_new

    override fun initTitleBar(titleBar: CommonTitleBar?) {
        titleBar?.run {
            centerSearchRightImageView.gone()
            setListener { v, action, extra ->
                when (action) {
                    CommonTitleBar.ACTION_SEARCH -> {
                        SearchActivity.jump(requireActivity())
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = ConfigManager.generateSource(ConfigManager.OKZYW)
    }

    override fun initView() {
        super.initView()
        statusView.run {
            failRetryClickListener = {
                initData()
            }
        }
    }

    override fun initListener() {
        super.initListener()
        faBtn.setOnClickListener {
            //选择视频源
            XPopup.Builder(requireActivity())
                .asCenterList("选择视频源",
                    ConfigManager.sourceConfigs.values.map { it.name }.toTypedArray(),
                    OnSelectListener { position, text ->
                        source = ConfigManager.generateSource(text)
                        initData()
                    })
                .show()
        }
    }

    override fun initData() {
        super.initData()
        titleBar?.centerSearchEditText?.hint = source?.name.textOrDefault("搜索")
        statusView.setLoadingStatus()
        source?.requestHomeData {
            if (it == null) {
                statusView.setFailStatus()
                return@requestHomeData
            }
            val videoList = it.videoList
            classifyList = it.classifyList.filter { classify ->
                !classify.id.isNullOrBlank() && !classify.name.isNullOrBlank()
            } as ArrayList<Classify>
            viewpager.adapter = ViewPageAdapter()
            tabLayout.setupWithViewPager(viewpager)
            statusView.setSuccessStatus()
        }
    }


    inner class ViewPageAdapter : FragmentPagerAdapter(
        activity!!.supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment {
            return HomeChannelFragment.instance(classifyList!![position].id.toString())
        }

        override fun getCount(): Int = classifyList?.size ?: 0

        override fun getPageTitle(position: Int): CharSequence? {
            return classifyList?.get(position)?.name
        }

    }
}