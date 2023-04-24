package com.example.QuizBattle.controller.ScreenAndMediaControllers

import android.media.MediaPlayer
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.model.AudioTrack
import kotlin.random.Random
/**

MediaController is a class responsible for managing background music and sound effects in the Quiz Battle app.
It works with the GameActivity to control the playback of different audio tracks based on the app's state.
@param context The main activity of the app.
 */
class MediaController(private val context: GameActivity)
{
    private var mediaPlayer: MediaPlayer?=null
    private val backGroundTrack: AudioTrack= AudioTrack("CASIOPERA",R.raw.music)
    private val quizTrack: AudioTrack= AudioTrack("Quiz",R.raw.quiz)
    private val resultsTrack:AudioTrack= AudioTrack("Results",R.raw.results)

    fun playBackGroundTrack() {
        mediaPlayer = MediaPlayer.create(context,backGroundTrack.resourceId)
        mediaPlayer?.apply {
            isLooping = true
            val duration= mediaPlayer!!.duration
            seekTo(Random.nextInt(duration))
            setVolume(0.6f, 0.6f)
            start()
        }
    }

    fun playResultsTrack() {
        mediaPlayer = MediaPlayer.create(context,resultsTrack.resourceId)
        mediaPlayer?.apply {
            isLooping = false
            setVolume(0.6f, 0.6f)
            start()
        }
    }

    fun playQuizTrack() {
        mediaPlayer = MediaPlayer.create(context,quizTrack.resourceId)
        mediaPlayer?.apply {
            isLooping = true
            setVolume(0.6f, 0.6f)
            start()
        }
    }
    fun pause(){
        mediaPlayer?.pause()
    }

}