package com.zyr.nice.feature.discovery

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.network.datasource.MyRetrofitDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoveryViewModel : ViewModel() {

    private val _datum = MutableStateFlow<List<Song>>(emptyList())
    val datum: StateFlow<List<Song>> = _datum

    private val _hasMore = mutableStateOf(true)
    private val _totalPage = mutableIntStateOf(0)
    private var _currentPage = mutableIntStateOf(0)
    private val _pageSize = mutableIntStateOf(15)

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
            val resp = MyRetrofitDatasource.songs(_currentPage.intValue, _pageSize.intValue)

            resp.data?.let {
                _datum.value = it.data ?: emptyList()
                _totalPage.intValue = it.totalPages
                Log.d(TAG, "loadData: ${it.totalPages}")
                if (_currentPage.intValue + 1 >= _totalPage.intValue) {
                    _hasMore.value = false
                }
                _currentPage.intValue++
            }

            Log.d(TAG, "loadData: ${resp.data?.data}")

            // 在这的代码会等待上面代码执行完才运行
        }
    }

    fun loadMore() {
        Log.d(TAG, "loadMore: ${_hasMore.value}")
        if (!_hasMore.value) return
        viewModelScope.launch {
            val resp = MyRetrofitDatasource.songs(_currentPage.intValue, _pageSize.intValue)
            resp.data?.let {
                _datum.update { it.plus(resp.data?.data ?: emptyList()) }
                _totalPage.intValue = it.totalPages ?: 0
                if (_currentPage.intValue <= _totalPage.intValue) {
                    _hasMore.value = false
                }
                _currentPage.intValue++
            }
//            _datum.update { it.plus(resp.data?.data ?: emptyList()) }
//            _datum.value.plus(resp.data?.data ?: emptyList())
//            Log.d(TAG, "loadData: ${resp.data?.data}")
        }
    }

    companion object {
        const val TAG = "DiscoveryViewModel"
    }
}