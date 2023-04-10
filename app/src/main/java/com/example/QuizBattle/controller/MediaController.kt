package com.example.QuizBattle.controller

import android.media.MediaPlayer
import com.example.QuizBattle.R
import com.example.QuizBattle.model.AudioTrack
import kotlin.random.Random

class MediaController(private val context:GameController)
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
            setVolume(0.6f, 0.6f) // Adjust the volume, if needed
            start()
        }
    }

    fun playResultsTrack() {
        mediaPlayer = MediaPlayer.create(context,resultsTrack.resourceId)
        mediaPlayer?.apply {
            isLooping = false
            setVolume(0.6f, 0.6f) // Adjust the volume, if needed
            start()
        }
    }

    fun playQuizTrack() {
        mediaPlayer = MediaPlayer.create(context,quizTrack.resourceId)
        mediaPlayer?.apply {
            isLooping = true
            setVolume(0.6f, 0.6f) // Adjust the volume, if needed
            start()
        }
    }
    fun pause(){
        mediaPlayer?.pause()
    }

    fun stop(){
        mediaPlayer?.stop()
    }
    fun release(){
        mediaPlayer?.release()
        mediaPlayer=null
    }
}