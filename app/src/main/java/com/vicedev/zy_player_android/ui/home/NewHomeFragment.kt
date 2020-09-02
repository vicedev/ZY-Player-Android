package com.vicedev.zy_player_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.sources.BaseSource
import com.vicedev.zy_player_android.sources.OKZYWSource
import com.vicedev.zy_player_android.sources.bean.Classify
import com.vicedev.zy_player_android.ui.BaseFragment
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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = OKZYWSource()
    }

    override fun initView() {
        super.initView()
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
            return HomeChannelFragment()
        }

        override fun getCount(): Int = classifyList?.size ?: 0

        override fun getPageTitle(position: Int): CharSequence? {
            return classifyList?.get(position)?.name
        }

    }
}