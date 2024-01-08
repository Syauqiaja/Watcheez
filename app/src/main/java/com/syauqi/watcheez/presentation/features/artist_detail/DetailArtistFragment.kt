package com.syauqi.watcheez.presentation.features.artist_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.syauqi.watcheez.R
import com.syauqi.watcheez.databinding.FragmentDetailArtistBinding
import com.syauqi.watcheez.presentation.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailArtistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailArtistFragment : BaseFragment<FragmentDetailArtistBinding>(FragmentDetailArtistBinding::inflate){
    private val args : DetailArtistFragmentArgs by navArgs()
    override fun initView() {
        super.initView()
        val people = args.people
        binding.apply {
            tvActorName.text = people.name
            tvArtistPopularity.text = String.format("%.2f", people.popularity)
            Glide.with(this@DetailArtistFragment).load(people.photoUrl).into(ivActorProfileHead)
        }
    }

}