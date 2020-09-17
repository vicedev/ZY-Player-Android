package com.vicedev.zy_player_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.common.ConfigManager
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.bean.Classify
import com.vicedev.zy_player_android.ui.BaseFragment
import com.vicedev.zy_player_android.ui.channel.HomeChannelFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_home.statusView
import kotlinx.android.synthetic.main.fragment_home_source.*

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/13 12:25
 * @desc
 */
class HomeSourceFragment : BaseFragment() {
    private var classifyList = ArrayList<Classify>()

    private var source: BaseSource? = null


    override fun getLayoutId(): Int = R.layout.fragment_home_source

    override fun initTitleBar(titleBar: CommonTitleBar?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = ConfigManager.curUseSourceConfig()
    }

    override fun initListener() {
        super.initListener()
        statusView.run {
            failRetryClickListener = {
                initData()
            }
        }
    }


    override fun initData() {
        super.initData()
        statusView.setLoadingStatus()
        source?.requestHomeData {
            if (it == null) {
                statusView.setFailStatus()
                return@requestHomeData
            }
            classifyList.clear()
            classifyList.add(Classify("new", "最新"))
            classifyList.addAll(it.classifyList.filter { classify ->
                !classify.id.isNullOrBlank() && !classify.name.isNullOrBlank()
            } as ArrayList<Classify>)
            viewpager.adapter = ViewPageAdapter()
            viewpager.offscreenPageLimit = 100
            tabLayout.setupWithViewPager(viewpager)
            statusView.setSuccessStatus()
        }
    }


    inner class ViewPageAdapter : FragmentPagerAdapter(
        childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment {
            return HomeChannelFragment.instance(classifyList[position].id.toString())
        }

        override fun getCount(): Int = classifyList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return classifyList[position].name
        }
    }

}