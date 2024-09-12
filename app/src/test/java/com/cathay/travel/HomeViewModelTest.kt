package com.cathay.travel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cathay.travel.model.Resource
import com.cathay.travel.model.news.News
import com.cathay.travel.model.news.NewsListModel
import com.cathay.travel.repository.ITravelRepository
import com.cathay.travel.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var travelRepository: ITravelRepository
    private val mainThreadSurrogate = Dispatchers.Unconfined
    private lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(travelRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // 重置 Main dispatcher
    }

    @Test
    fun getNewsSuccessTest() {
        val lang = "zh-tw"
        val newsList = listOf(
            News(
                id = 1,
                begin = 1,
                end = 1,
                description = "description",
                files = null,
                links = null,
                modified = "",
                posted = "",
                title = "TestTitle",
                url = "http://www.google.com"
            )
        )
        val newsSuccessResponse = flowOf(
            Resource.Success(
                data = NewsListModel(newsList = newsList, total = newsList.size)
            )
        )
        coEvery { travelRepository.getNews(lang) }.returns(newsSuccessResponse)
        homeViewModel.getNews(lang)
        coVerify { travelRepository.getNews(eq(lang)) }
        Assert.assertEquals(homeViewModel.newsListLiveData.value, newsList)
    }

    @Test
    fun getNewsFailedTest() {
        val lang = "invalidLang"
        val errorMessage = "ErrorMessage"
        val newsFailedResponse: Flow<Resource<NewsListModel>> =
            flowOf(Resource.Error(message = errorMessage))
        coEvery { travelRepository.getNews(lang) }.returns(newsFailedResponse)
        homeViewModel.getNews(lang)
        coVerify { travelRepository.getNews(eq(lang)) }
        Assert.assertEquals(homeViewModel.toastLiveData.value?.peekContent(), errorMessage)
    }
}