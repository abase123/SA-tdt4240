package com.example.QuizBattle.controller.gameStates.SelectTheme

interface OnThemeSelectListener {
    /*
    This method is called when a theme change event occurs.
    @param theme The new theme that has been selected.
    */
    fun onThemeSelected(theme: String)
}