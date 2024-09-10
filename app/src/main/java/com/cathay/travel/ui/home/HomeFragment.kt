package com.cathay.travel.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cathay.travel.R
import com.cathay.travel.model.news.News
import com.cathay.travel.model.place.Place
import com.cathay.travel.databinding.FragmentHomeBinding
import com.cathay.travel.extension.observe
import com.cathay.travel.ui.home.adapter.NewsAdapter
import com.cathay.travel.ui.home.adapter.PlaceAdapter
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
        observe(viewModel.loadingLiveData, ::onLoading)
        observe(viewModel.newsListLiveData, ::onNewList)
        observe(viewModel.placeCountLiveData, ::onPlaceCount)
        observe(viewModel.placeListLiveData, ::onPlaceList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsListRv.setHasFixedSize(true)
        binding.newsListRv.layoutManager = LinearLayoutManager(activity)
        binding.newsListRv.adapter = newsAdapter

        binding.placeListRv.setHasFixedSize(true)
        binding.placeListRv.layoutManager = LinearLayoutManager(activity)
        binding.placeListRv.adapter = placeAdapter

        viewModel.getNews()
        viewModel.getPlaceList()
    }

    private fun onLoading(loading: Boolean) {
        binding.loadingView.visibility =
            if (loading) View.VISIBLE else View.GONE
    }

    private fun onPlaceCount(count: Int) {
        binding.placeCountTv.text = String.format(
            getString(R.string.label_place_count),
            count
        )
    }

    private fun onNewList(newsList: List<News>) {
        newsAdapter.setData(newsList)
    }

    private fun onPlaceList(placeList: List<Place>) {
        placeAdapter.setData(placeList)
    }

    override fun onNewsClicked(news: News) {
        if (news.url.isNullOrEmpty())
            return
        viewModel.clickNews(news)
        findNavController().navigate(R.id.action_Home_to_News)
    }

    override fun onPlaceClicked(place: Place) {
        viewModel.clickPlace(place)
        findNavController().navigate(R.id.action_Home_to_Place)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}