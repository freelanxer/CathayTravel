package com.cathay.travel.ui.place

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cathay.travel.R
import com.cathay.travel.databinding.FragmentPlaceBinding
import com.cathay.travel.model.place.Place
import com.cathay.travel.ui.home.HomeViewModel
import com.cathay.travel.ui.place.adapter.PhotoPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceFragment : Fragment() {
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!
    val viewModel: HomeViewModel by activityViewModels()
    val photoAdapter: PhotoPagerAdapter by lazy { PhotoPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoVp.adapter = photoAdapter
    }

    override fun onResume() {
        super.onResume()
        val place = viewModel.selectedPlaceLiveData.value
        place?.let {
            setActionBarTitle(it.name)
            setContent(place)
        }
    }

    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun setContent(place: Place) {
        // Open time
        binding.openTimeCard.visibility =
            if (place.openTime.isNullOrEmpty()) View.GONE else View.VISIBLE
        place.openTime?.let {
            binding.openTimeTv.text =
                String.format(getString(R.string.label_open_time), it)
        }
        // Address
        binding.addressCard.visibility =
            if (place.address.isNullOrEmpty()) View.GONE else View.VISIBLE
        place.address?.let {
            binding.addressTv.text =
                String.format(getString(R.string.label_address), it)
        }
        // Telephone
        binding.telephoneCard.visibility =
            if (place.tel.isNullOrEmpty()) View.GONE else View.VISIBLE
        place.tel?.let {
            binding.telephoneTv.text =
                String.format(getString(R.string.label_telephone), it)
        }
        // Official site
        binding.officialSiteCard.visibility =
            if (place.officialSite.isNullOrEmpty()) View.GONE else View.VISIBLE
        place.officialSite?.let {
            binding.officialSiteTv.text =
                String.format(getString(R.string.label_official_site), it)
        }
        // Introduction
        place.introduction?.let {
            binding.introTv.text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
        }
        // Photo Pager
        val imageUrlList = place.images?.mapNotNull { it.src }
        binding.photoVp.visibility =
            if (imageUrlList.isNullOrEmpty()) View.GONE else View.VISIBLE
        imageUrlList?.let {
            photoAdapter.setPhoto(imageUrlList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}