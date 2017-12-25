package com.example.android.miwok

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: SimpleFragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SimpleFragmentPagerAdapter(supportFragmentManager, applicationContext)

        // Set the adapter onto the view pager
        view_pager.adapter = adapter

        sliding_tabs.setupWithViewPager(view_pager)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            sliding_tabs.tabMode = TabLayout.MODE_FIXED
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            sliding_tabs.tabMode = TabLayout.MODE_SCROLLABLE
        }
    }
}