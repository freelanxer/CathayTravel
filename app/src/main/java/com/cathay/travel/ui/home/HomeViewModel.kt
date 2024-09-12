package com.cathay.travel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cathay.travel.model.Event
import com.cathay.travel.model.Resource
import com.cathay.travel.model.news.News
import com.cathay.travel.model.place.Place
import com.cathay.travel.repository.ITravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 首頁 ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val travelRepository: ITravelRepository
): ViewModel() {
    // 景點數 LiveData
    private val _placeCountLiveData = MutableLiveData(0)
    val placeCountLiveData: LiveData<Int> get() = _placeCountLiveData

    // 景點列表 LiveData
    private val _placeListLiveData = MutableLiveData<List<Place>>()
    val placeListLiveData: LiveData<List<Place>> get() = _placeListLiveData

    // 被點擊景點 LiveData
    private val _selectedPlaceLiveData = MutableLiveData<Place>()
    val selectedPlaceLiveData: LiveData<Place> get() = _selectedPlaceLiveData

    // 最新消息列表 LiveData
    private val _newsListLiveData = MutableLiveData<List<News>>()
    val newsListLiveData: LiveData<List<News>> get() = _newsListLiveData

    // 被點擊最新消息 LiveData
    private val _selectedNewsLiveData = MutableLiveData<News>()
    val selectedNewsLiveData: LiveData<News> get() = _selectedNewsLiveData

    // 資料加載中 LiveData
    private val _loadingLiveData = MutableLiveData<Event<Boolean>>()
    val loadingLiveData: LiveData<Event<Boolean>> get() = _loadingLiveData

    // Toast 訊息 LiveData
    private val _toastLiveData = MutableLiveData<Event<String>>()
    val toastLiveData: LiveData<Event<String>> get() = _toastLiveData

    /**
     * 取得最新消息資資料
     */
    fun getNews(lang: String) {
        viewModelScope.launch {
            val resourceFlow = withContext(Dispatchers.IO) {
                travelRepository.getNews(lang)
            }
            resourceFlow.collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _newsListLiveData.value = response.data?.newsList ?: listOf()
                        _loadingLiveData.value = Event(false)
                    }
                    is Resource.Loading -> {
                        _loadingLiveData.value = Event(true)
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            _toastLiveData.value = Event(it)
                        }
                        _loadingLiveData.value = Event(false)
                    }
                }
            }
        }
    }

    /**
     * 取得景點資料
     */
    fun getPlaceList(lang: String) {
        viewModelScope.launch {
            val resourceFlow = withContext(Dispatchers.IO) {
                travelRepository.getPlaceList(lang)
            }
            resourceFlow.collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _placeListLiveData.value = response.data?.placeList ?: listOf()
                        _placeCountLiveData.value = response.data?.total
                        _loadingLiveData.value = Event(false)
                    }
                    is Resource.Loading -> {
                        _loadingLiveData.value = Event(true)
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            _toastLiveData.value = Event(it)
                        }
                        _loadingLiveData.value = Event(false)
                    }
                }
            }
        }
    }

    /**
     * 更新被點擊最新消息 LiveData
     */
    fun clickNews(news: News) {
        _selectedNewsLiveData.value = news
    }

    /**
     * 更新被點擊景點 LiveData
     */
    fun clickPlace(place: Place) {
        _selectedPlaceLiveData.value = place
    }

}