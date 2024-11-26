package com.zyr.nice.feature.main

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.zyr.nice.core.config.Config
import com.zyr.nice.core.model.SONG_EMPTY
import com.zyr.nice.core.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel() : ViewModel() {

    private var player: ExoPlayer? = null

    private var _curSong: MutableStateFlow<Song> = MutableStateFlow<Song>(SONG_EMPTY())
    val curSong: StateFlow<Song> = _curSong

    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    init {

    }

    /**
     * 初始化播放器
     */
    @OptIn(UnstableApi::class)
    fun initPlayer(context: Context) {
        if (player != null) return

        player = ExoPlayer.Builder(context).build()
    }

    fun addMediaItem(song: Song) {
        _curSong.value = song
        player?.let {
            val mediaItem = MediaItem.fromUri(Uri.parse(Config.RESOURCE_ENDPOINT.format(song.uri)))

            it.addMediaItem(mediaItem)
            it.prepare()

            Log.d(TAG, "addMediaItem: xxxxxxxxxxxxx")
        }
    }

    fun stopAndRelease() {
        player?.let {
            it.stop()
            it.release()
            player = null
            _isPlaying.value = false
        }
    }

    fun play() {
        player?.let {
            it.play()
            _isPlaying.value = true
        }
    }

    fun pause() {
        player?.let {
            it.pause()
            _isPlaying.value = false
        }
    }

    companion object {
        val TAG = "MainViewModel"
    }

}