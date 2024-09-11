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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val travelRepository: ITravelRepository
): ViewModel() {
    private val _newsListLiveData = MutableLiveData<List<News>>()
    val newsListLiveData: LiveData<List<News>> get() = _newsListLiveData

    private val _placeListLiveData = MutableLiveData<List<Place>>()
    val placeListLiveData: LiveData<List<Place>> get() = _placeListLiveData

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _toastLiveData = MutableLiveData<Event<String>>()
    val toastLiveData: LiveData<Event<String>> get() = _toastLiveData

    private val _placeCountLiveData = MutableLiveData(0)
    val placeCountLiveData: LiveData<Int> get() = _placeCountLiveData

    private val _selectedPlaceLiveData = MutableLiveData<Place>()
    val selectedPlaceLiveData: LiveData<Place> get() = _selectedPlaceLiveData

    private val _selectedNewsLiveData = MutableLiveData<News>()
    val selectedNewsLiveData: LiveData<News> get() = _selectedNewsLiveData

    fun getNews(lang: String) {
        viewModelScope.launch {
            travelRepository.getNews(lang).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _newsListLiveData.value = response.data?.newsList ?: listOf()
                        _loadingLiveData.value = false
                    }
                    is Resource.Loading -> {
                        _loadingLiveData.value = true
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            _toastLiveData.value = Event(it)
                        }
                        _loadingLiveData.value = false
                    }
                }
            }
        }
    }

    fun getPlaceList(lang: String) {
        viewModelScope.launch {
            travelRepository.getPlaceList(lang).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _placeListLiveData.value = response.data?.placeList ?: listOf()
                        _placeCountLiveData.value = response.data?.total
                        _loadingLiveData.value = false
                    }
                    is Resource.Loading -> {
                        _loadingLiveData.value = true
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            _toastLiveData.value = Event(it)
                        }
                        _loadingLiveData.value = false
                    }
                }
            }
        }
    }

    fun clickNews(news: News) {
        _selectedNewsLiveData.value = news
    }

    fun clickPlace(place: Place) {
        _selectedPlaceLiveData.value = place
    }

}