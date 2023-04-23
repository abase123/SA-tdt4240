package com.example.QuizBattle.model.QuizModel

import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
/**

QuizTimer is a utility class that provides functionality to manage and track the time spent

on answering quiz questions. It uses CountDownTimer to keep track of the time spent on each question

and calculates the total time spent on the quiz. It can be used in conjunction with other quiz

related classes to provide a complete quiz experience.

@property timeLimitPerQuestion the time limit in milliseconds for each quiz question

@property intervalInMilli the interval time in milliseconds for the CountDownTimer

@property startTime the start time for each question in milliseconds since boot

@property countDownTimer the CountDownTimer instance that is used to track the time spent on a question

@property timeUsedOneQuestion the time used to answer a single question

@property totalTimeUsed the total time used to answer all questions
 */
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