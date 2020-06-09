package com.vicedev.zy_player_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vicedev.zy_player_android.R
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 15:16
 */
abstract class BaseFragment : Fragment() {

    lateinit var rootView: ViewGroup
    var titleBar: CommonTitleBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView =
            (inflater.inflate(R.layout.fragment_base, container, false) as ViewGroup).apply {
                addView(inflater.inflate(getLayoutId(), this, false))
            }
        return rootView
    }


    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleBar = view.findViewById(R.id.title_bar) as? CommonTitleBar
        initTitleBar(titleBar)
        initView()
        initListener()
        initData()
    }

    abstract fun initTitleBar(titleBar: CommonTitleBar?)

    open fun initView() {}
    open fun initListener() {}
    open fun initData() {}
}