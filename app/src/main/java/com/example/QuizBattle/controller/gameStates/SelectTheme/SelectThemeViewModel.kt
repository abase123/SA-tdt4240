package com.example.QuizBattle.controller.gameStates.SelectTheme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectThemeViewModel:ViewModel() {
    val categoryViewModel: MutableLiveData<String> = MutableLiveData()

}