package com.example.QuizBattle.model

/**
 * In the package `com.example.QuizBattle.model`, the `AudioTrack` data class represents an individual
 * audio track used within the QuizBattle application. Each `AudioTrack` object holds essential
 * information about the audio file, such as its title and resource identifier (resourceId). This class
 * is designed to help manage and organize audio resources within the app effectively, making it easier
 * to retrieve and play specific audio files during gameplay.
 */
data class AudioTrack (val title: String, val resourceId:Int)
