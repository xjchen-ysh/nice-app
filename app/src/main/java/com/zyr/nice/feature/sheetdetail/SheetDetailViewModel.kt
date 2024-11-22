package com.zyr.nice.feature.sheetdetail

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
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


    private var mediaPlayer: ExoPlayer? = null
    val isPlaying = MutableLiveData<Boolean>(false)
    fun initPlayer(context: Context, uri: Uri) {

        mediaPlayer = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(uri)
        mediaPlayer?.let {
            it.setMediaItem(mediaItem)
            it.prepare()
        }

    }

    fun play() {
        mediaPlayer?.play()
        isPlaying.value = true
    }

    fun pause() {
        mediaPlayer?.pause()
        isPlaying.value = false
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

}