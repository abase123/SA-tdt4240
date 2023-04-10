package com.example.QuizBattle.model.QuizModel

import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log

class QuizTimer {
   private val timeLimitPerQuestion = 10_000L // 9 sec
   val intervalInMilli= 1_000L //300 millisec
   private var startTime=0L
   private lateinit var countDownTimer: CountDownTimer
   private var timeUsedOneQuestion =0L
   private var totalTimeUsed = 0L


   fun initTimer(){
      totalTimeUsed=0L
   }
   fun startTimer() {
      startTime= SystemClock.elapsedRealtime()
      countDownTimer = object : CountDownTimer(timeLimitPerQuestion, intervalInMilli){
         override fun onTick(millisUntilFinished: Long) {
            val elapsedTimeMillis = SystemClock.elapsedRealtime() - startTime
         }
         override fun onFinish() {
            timeUsedOneQuestion=timeLimitPerQuestion
         }
      }
   }
   fun resetTimer(){
      countDownTimer.cancel()
      startTime=0L
      Log.d("totalTimeUsed", "$totalTimeUsed:")
      Log.d("timeUsedOneQuestion", "$timeUsedOneQuestion:")
      totalTimeUsed += timeUsedOneQuestion
      timeUsedOneQuestion=0
   }

   fun getElapsedTime():Float{
      timeUsedOneQuestion=SystemClock.elapsedRealtime()-startTime
      timeUsedOneQuestion
      return (timeUsedOneQuestion /1000).toFloat()
   }

   fun getTotalTimeUsed(): Float {
      Log.d("totalTimeUsed", "$totalTimeUsed:")
      return (totalTimeUsed / 1000f).toFloat()
   }

}