package com.example.QuizBattle.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import com.example.QuizBattle.R

class SettingsActivity : MainActivity() {
    private lateinit var saveButton: Button
    private lateinit var soundBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        saveButton = findViewById(R.id.button_save)
        soundBar=findViewById(R.id.seekbar_sound)
        saveButton.setOnClickListener {
            Intent(this, HomeMenuActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}