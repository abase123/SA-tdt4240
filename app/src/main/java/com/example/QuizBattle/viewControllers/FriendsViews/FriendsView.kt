package com.example.QuizBattle.views.FriendsViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.PlayerViewModel
import com.example.QuizBattle.viewControllers.FriendsViews.FriendsListView
import com.example.QuizBattle.viewControllers.FriendsViews.FriendsRequestsView
import com.example.QuizBattle.viewControllers.FriendsViews.SearchFriendsView
import com.google.android.material.tabs.TabLayout

private lateinit var tabLayout: TabLayout
private lateinit var viewPager: ViewPager
/**
 * The FriendsView class is a Fragment that serves as the main view for the friends management
 * section of the QuizBattle application. It displays a TabLayout and ViewPager that allow users
 * to navigate between three different tabs: Search Friends, See Friends List, and Friends Requests.
 * Each tab displays a different Fragment, providing functionality for searching for friends,
 * viewing the user's friends list, and managing incoming friend requests.
 */
class FriendsView : Fragment(){
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var userNameText: TextView
    private lateinit var userScoreText: TextView
    private lateinit var numQuizTakenText: TextView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameText=view.findViewById(R.id.username_user_card)
        userScoreText=view.findViewById(R.id.allTimeScore)
        numQuizTakenText=view.findViewById(R.id.quizzesPlayed)

        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]
        playerViewModel.player.observe(viewLifecycleOwner) { player ->
            player?.let {
                userNameText.text = it.displayName
                userScoreText.text= it.allTimeScore.toString()
                numQuizTakenText.text=it.numQuizzesTaken.toString()
            }
        }
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