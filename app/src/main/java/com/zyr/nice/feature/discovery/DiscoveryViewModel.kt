package com.zyr.nice.feature.discovery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.network.datasource.MyRetrofitDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscoveryViewModel : ViewModel() {

    private val _datum = MutableStateFlow<List<Song>>(emptyList());
    val datum: StateFlow<List<Song>> = _datum

    init {
        loadData()
    }

    private fun loadData() {
//        _datum.value = DiscoveryDataPreviewParameterProvider.songs

//        val jsonStr = Json.encodeToString(DiscoveryDataPreviewParameterProvider.songs)
//        Log.d(TAG, jsonStr)
//
//        val obj = Json.decodeFromString<List<Song>>(jsonStr)
//        Log.d(TAG, "jsonObj: $obj")

        viewModelScope.launch {
            val resp = MyRetrofitDatasource.songs(0, 15)
            _datum.value = resp.data?.data ?: emptyList()
            Log.d(TAG, "loadData: ${resp.data?.data}")

            // 在这的代码会等待上面代码执行完才运行
        }
    }

    companion object {
        const val TAG = "DiscoveryViewModel"
    }
}