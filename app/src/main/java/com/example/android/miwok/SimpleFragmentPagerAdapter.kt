package com.example.android.miwok

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by unknown on 17/12/17
 */

class SimpleFragmentPagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
        FragmentPagerAdapter(fragmentManager) {

    private val pageCount = 4

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> NumbersFragment()
            1 -> FamilyFragment()
            2 -> ColorsFragment()
            else -> PhrasesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {

            0 -> context.getString(R.string.category_numbers)
            1 -> context.getString(R.string.category_family)
            2 -> context.getString(R.string.category_colors)
            else -> context.getString(R.string.category_phrases)
        }
    }
}