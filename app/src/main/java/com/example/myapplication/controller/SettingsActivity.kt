package com.example.myapplication.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var saveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        saveButton = findViewById(R.id.button_save)
        saveButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }

        }

    }
}