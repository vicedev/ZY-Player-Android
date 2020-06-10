package com.vicedev.zy_player_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vicedev.zy_player_android.common.BackPressedCall

/**
 * @author vicedev1001@gmail.com
 * @date 2020/6/8 16:33
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initListener()
    }

    open fun initView() {
    }

    open fun initListener() {
    }

    abstract fun getLayoutId(): Int

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            it?.let {
                if ((it as? BackPressedCall)?.onBackPressed() == true) {
                    return
                }
            }
        }
        super.onBackPressed()
    }
}