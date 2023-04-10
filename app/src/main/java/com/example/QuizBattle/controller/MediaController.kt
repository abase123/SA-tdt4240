package com.example.QuizBattle.controller

import android.media.MediaPlayer
import com.example.QuizBattle.R
import com.example.QuizBattle.model.AudioTrack

class MediaController(private val context:GameController)
{
    private var mediaPlayer: MediaPlayer?=null
    private val backGroundTrack: AudioTrack= AudioTrack("CASIOPERA",R.raw.music)
    fun playBackGroundMuisc() {
        mediaPlayer = MediaPlayer.create(context,backGroundTrack.resourceId)
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