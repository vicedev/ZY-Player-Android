package com.vicedev.zy_player_android.ui

import android.os.SystemClock
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.vicedev.zy_player_android.R
import com.vicedev.zy_player_android.ui.collect.CollectFragment
import com.vicedev.zy_player_android.ui.home.NewHomeFragment
import com.vicedev.zy_player_android.ui.mine.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val fragmentArray = SparseArray<Fragment>(3)

    var mHits = LongArray(2)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        fragmentArray.put(R.id.navigation_home, NewHomeFragment())
        fragmentArray.put(R.id.navigation_collect, CollectFragment())
        fragmentArray.put(R.id.navigation_mine, MineFragment())
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
                    if (key == id) {
                        show(value)
                        if (value is CollectFragment) {
                            value.refresh()
                        }
                    } else {
                        hide(value)
                    }
                }
            }.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1);
        mHits[mHits.size - 1] = SystemClock.uptimeMillis()
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            super.onBackPressed();
        } else {
            ToastUtils.showShort("再按一次退出程序")
        }
    }
}
