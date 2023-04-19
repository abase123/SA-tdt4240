package com.example.QuizBattle.views.FriendsViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.QuizBattle.R
import com.google.android.material.tabs.TabLayout


private lateinit var tabLayout: TabLayout
private lateinit var viewPager: ViewPager


class FriendsView : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        val pagerAdapter = FriendsPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)

        viewPager.currentItem = 1 // Index of the "See Friends List" tab

        return view
    }

    // PagerAdapter for the ViewPager
    private inner class FriendsPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            // Return the appropriate fragment based on the tab position
            return when (position) {
                0 -> SearchFriendsView()
                1 -> FriendsListView()
                2 -> FriendsRequestsView()
                else -> throw IllegalArgumentException("Invalid tab position")
            }
        }

        override fun getCount(): Int {
            // Return the number of tabs
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Search Friends"
                1 -> "See Friends List"
                2 -> "Friends Requests"
                else -> null
            }
        }
    }
}