package com.vicedev.zy_player_android.ui

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.collect.CollectFragment
import com.vicedev.zy_player_android.ui.home.HomeFragment
import com.vicedev.zy_player_android.ui.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val fragmentArray = SparseArray<Fragment>(3)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        fragmentArray.put(R.id.navigation_home, HomeFragment())
        fragmentArray.put(R.id.navigation_collect, CollectFragment())
        fragmentArray.put(R.id.navigation_setting, SettingFragment())
        supportFragmentManager
            .beginTransaction()
            .apply {
                fragmentArray.forEach { key, value ->
                    add(R.id.container, value, key.toString())
                }
            }
            .commitAllowingStateLoss()
        switchPage(R.id.navigation_home)
    }

    override fun initListener() {
        super.initListener()
        navView.setOnNavigationItemSelectedListener {
            switchPage(it.itemId)
            true
        }
    }

    private fun switchPage(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                fragmentArray.forEach { key, value ->
                    if (key == id) show(value) else hide(value)
                }
            }.commitAllowingStateLoss()
    }
}
