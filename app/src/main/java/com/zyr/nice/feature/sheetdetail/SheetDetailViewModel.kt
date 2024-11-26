package com.zyr.nice.feature.sheetdetail

import android.content.Context
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.zyr.nice.core.model.SONG_EMPTY
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.network.datasource.MyRetrofitDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SheetDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val id: Long = checkNotNull(savedStateHandle[SHEET_ID])

    private val _data = MutableStateFlow<Song>(SONG_EMPTY())
    val topSong: StateFlow<Song> = _data


    private val _curDuration = MutableStateFlow<Float>(0f)
    val curDuration: StateFlow<Float> = _curDuration
    private val _durationUnit = MutableStateFlow<Float>(0f)
    private val _isUserInteraction = MutableStateFlow(false)
    val isUserInteraction: StateFlow<Boolean> = _isUserInteraction

    private var _durationTimer: CountDownTimer? = null;
    private val _timeLeft = MutableStateFlow(0L)

    fun onCurDurationChange(value: Float) {
        Log.d(
            "onCurDurationChange xxxxxxxxxxxxxx",
            "onCurDurationChange: $value -- ${_totalDuration.value} -- seekTo: ${(value * _totalDuration.value).toLong()}"
        )
        _curDuration.value = value
        mediaPlayer?.seekTo((value * _totalDuration.value).toLong())
    }


    private val _totalDuration = MutableStateFlow<Long>(0L)

    private var mediaPlayer: ExoPlayer? = null
    val isPlaying = MutableLiveData<Boolean>(false)

    @OptIn(UnstableApi::class)
    fun initPlayer(context: Context, uri: Uri) {

        if (mediaPlayer != null) return
        mediaPlayer = ExoPlayer.Builder(context).build()

        Log.d(TAG, "initPlayer: 初始化")

        val mediaItem = MediaItem.fromUri(uri)
        mediaPlayer?.let {
            it.setMediaItem(mediaItem)
            it.prepare()

            it.addAnalyticsListener(object : AnalyticsListener {
                val TAG = "AnalyticsListener"
                override fun onIsPlayingChanged(
                    eventTime: AnalyticsListener.EventTime,
                    isPlaying: Boolean
                ) {
                    Log.d(TAG, "isPlaying: $isPlaying")
                    super.onIsPlayingChanged(eventTime, isPlaying)
                    if (isPlaying) {
                        _totalDuration.value = it.duration
                        _durationUnit.value = 1f / it.duration * 1000

                    }
                }
            })
        }

    }

    fun play() {
        mediaPlayer?.play()
        Log.d(TAG, mediaPlayer.toString())
        isPlaying.value = true

        viewModelScope.launch {
            _durationTimer?.cancel()
            _durationTimer =
                object :
                    CountDownTimer(_totalDuration.value, 1000L) {
                    // 每次倒计时执行
                    override fun onTick(millisUntilFinished: Long) {
                        _timeLeft.value = millisUntilFinished / 1000 + 1
                        _isUserInteraction.value = false

                        _curDuration.value += _durationUnit.value
//                        Log.d(
//                            TAG,
//                            "onTick: ${_curDuration.value} "
//                        )
                    }

                    // 倒计时结束
                    override fun onFinish() {

                    }

                }.start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
        isPlaying.value = false

        Log.d(TAG, "pause ")
        Log.d(TAG, mediaPlayer.toString())

        _durationTimer?.cancel()
    }

    fun stopAndRelease() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying.value = false
    }

    fun isCurrentlyPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }


    init {
//        val TAG =
//            "SheetDetailViewModel"
//        Log.d(TAG, "SheetDetailViewModel: $id")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // 根据id获取到song
            val resp = MyRetrofitDatasource.songDetail(id)
            _data.value = resp.data ?: SONG_EMPTY()
        }
    }

    fun userInteraction() {
        _isUserInteraction.value = true
    }

    companion object {
        val TAG = "SheetDetailViewModel"
    }

}