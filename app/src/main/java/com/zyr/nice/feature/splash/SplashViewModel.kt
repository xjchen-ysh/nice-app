package com.zyr.nice.feature.splash

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SplashViewModel : ViewModel() {

    private var timer: CountDownTimer? = null

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> = _timeLeft

    val navigateToMain = MutableStateFlow(false)

    init {
        delayToNext()
    }

    private fun delayToNext(time: Long = 3000) {
        timer = object : CountDownTimer(time, 1000L) {
            // 每次倒计时执行
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished / 1000 + 1
            }

            // 倒计时结束
            override fun onFinish() {
                toNext()
            }

        }.start()
    }

    private fun toNext() {
        navigateToMain.value = true
    }

    fun onSkipAdClick() {
        timer?.cancel()
        toNext()
    }
}