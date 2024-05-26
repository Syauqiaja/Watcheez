package com.syauqi.watcheez.presentation.features.movie_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.syauqi.watcheez.databinding.DialogWatchOnBinding
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.presentation.features.movie_detail.adapter.WatchProviderAdapter

class WatchOnDialog(private val movieProviders: MovieProviders): DialogFragment() {
    lateinit var binding: DialogWatchOnBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWatchOnBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProviders.apply {
            layoutManager = FlexboxLayoutManager(requireContext()).also {
                it.justifyContent = JustifyContent.CENTER
                it.alignItems = AlignItems.CENTER
                it.flexDirection = FlexDirection.ROW
                it.flexWrap = FlexWrap.WRAP
            }
            adapter = WatchProviderAdapter().also {
                it.setData(movieProviders.providers)
                it.onItemClick = openLink(movieProviders.link)
            }
        }
        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun openLink(link: String): ((MovieProviders.WatchProvider) -> Unit) = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }
    companion object{
        const val TAG = "WATCH_ON_DIALOG"
    }
}