package com.ozantopuz.sixtcodingchallenge.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ozantopuz.sixtcodingchallenge.MainViewModel
import com.ozantopuz.sixtcodingchallenge.R
import com.ozantopuz.sixtcodingchallenge.databinding.FragmentListBinding
import com.ozantopuz.sixtcodingchallenge.util.delegate.viewBinding
import com.ozantopuz.sixtcodingchallenge.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding: FragmentListBinding by viewBinding()
    private val adapter: CarAdapter by lazy { CarAdapter() }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            MainViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.recyclerView.adapter = adapter

        with(viewModel) {
            carsLiveData.observe(viewLifecycleOwner, { list ->
                binding.textView.isVisible = list.isNullOrEmpty()
                adapter.setList(list)
            })
            errorLiveData.observe(viewLifecycleOwner, { message -> context.toast(message) })
        }
    }
}