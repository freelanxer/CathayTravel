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
import com.cathay.travel.data.news.News
import com.cathay.travel.data.place.Place
import com.cathay.travel.databinding.FragmentHomeBinding
import com.cathay.travel.extension.observe
import com.cathay.travel.ui.home.adapter.NewsAdapter
import com.cathay.travel.ui.home.adapter.PlaceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(viewModel) }
    private val placeAdapter: PlaceAdapter by lazy { PlaceAdapter(viewModel) }

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
        observe(viewModel.placeCountLiveData, ::onPlaceCount)
        observe(viewModel.placeListLiveData, ::onPlaceList)
        observe(viewModel.newsListLiveData, ::onNewList)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}