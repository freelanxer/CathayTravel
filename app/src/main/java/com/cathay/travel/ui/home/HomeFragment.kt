package com.cathay.travel.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cathay.travel.R
import com.cathay.travel.model.news.News
import com.cathay.travel.model.place.Place
import com.cathay.travel.databinding.FragmentHomeBinding
import com.cathay.travel.extension.observe
import com.cathay.travel.model.EventObserver
import com.cathay.travel.ui.home.adapter.NewsAdapter
import com.cathay.travel.ui.home.adapter.PlaceAdapter
import com.cathay.travel.utils.LanguageUtil
import com.cathay.travel.utils.SharedPreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), NewsAdapter.Listener, PlaceAdapter.Listener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }
    private val placeAdapter: PlaceAdapter by lazy { PlaceAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 觀察 ViewModel LiveData
        observe(viewModel.newsListLiveData, ::onNewList)
        observe(viewModel.placeCountLiveData, ::onPlaceCount)
        observe(viewModel.placeListLiveData, ::onPlaceList)
        observe(viewModel.placeListLiveData, ::onPlaceList)
        viewModel.loadingLiveData.observe(this, EventObserver {
            binding.loadingView.visibility =
                if (it) View.VISIBLE else View.GONE
        })
        viewModel.toastLiveData.observe(this, EventObserver {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 設定列表 Adapter
        binding.newsListRv.adapter = newsAdapter
        binding.placeListRv.adapter = placeAdapter
        // 取得目前語言，並發出 API 請求
        val langCode = activity?.let { SharedPreferenceUtil.getLanguageCode(it) }
        langCode?.let {
            val code = LanguageUtil.convertLangTagToCode(it)
            viewModel.getNews(code)
            viewModel.getPlaceList(code)
        }
    }

    /**
     * View 顯示景點數
     */
    private fun onPlaceCount(count: Int) {
        binding.placeCountTv.text = String.format(
            getString(R.string.label_place_count),
            count
        )
    }

    /**
     * View 顯示最新消息列表
     */
    private fun onNewList(newsList: List<News>) {
        newsAdapter.setData(newsList)
    }

    /**
     * View 顯示景點列表
     */
    private fun onPlaceList(placeList: List<Place>) {
        placeAdapter.setData(placeList)
    }

    /**
     * 最新消息列表點擊事件
     */
    override fun onNewsClicked(news: News) {
        if (news.url.isNullOrEmpty())
            return
        viewModel.clickNews(news)
        findNavController().navigate(
            HomeFragmentDirections.actionPlaceToWeb(
                title = getString(R.string.fragment_label_news),
                url = news.url
            )
        )
    }

    /**
     * 景點列表點擊事件
     */
    override fun onPlaceClicked(place: Place) {
        viewModel.clickPlace(place)
        findNavController().navigate(R.id.action_Home_to_Place)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}