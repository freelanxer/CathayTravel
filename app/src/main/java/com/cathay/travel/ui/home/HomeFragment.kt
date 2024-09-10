package com.cathay.travel.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cathay.travel.R
import com.cathay.travel.data.news.News
import com.cathay.travel.data.place.Place
import com.cathay.travel.databinding.FragmentHomeBinding
import com.cathay.travel.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

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
        observe(viewModel.placeListLiveData, ::onPlaceList)
        observe(viewModel.newsListLiveData, ::onNewList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_Home_to_News)
        }

        binding.placeBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToPlace(index = 0))
        }

        viewModel.getPlaceList()
    }

    private fun onLoading(loading: Boolean) {
        binding.loadingView.visibility =
            if (loading) View.VISIBLE else View.GONE
    }

    private fun onNewList(list: List<News>) {
        list.let {

        }
    }

    private fun onPlaceList(data: List<Place>) {
        data.let { placeList ->
            binding.textviewFirst.text = placeList.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}