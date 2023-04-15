package com.example.QuizBattle.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.ViewChangeListener

/**
 * A simple [Fragment] subclass.
 * Use the [HomeView.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var gameController: GameController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewChangeListener) {
            viewChangeListener = context
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        viewChangeListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameController = activity as GameController
        gameController.fragmentLoadingState.setLoading(false)
        val playFriendBtn=view.findViewById<Button>(R.id.playFriendButton)
        val playDailyBtn=view.findViewById<Button>(R.id.playdailyButton)

        playFriendBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.PLAY_FRIEND)
        }

        playDailyBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_DAILY_QUIZ)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}