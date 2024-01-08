package com.syauqi.watcheez.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<VB: ViewBinding>(
    private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> VB
): Fragment() {
    private var _binding : VB? = null

    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    val binding: VB get() = _binding!!

    open fun initView(){}
    open fun observeViewModel(){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        observeViewModel()
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}