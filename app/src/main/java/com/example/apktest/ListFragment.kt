package com.example.apktest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apktest.databinding.RecyclerViewBinding

class ListFragment : Fragment(R.layout.recycler_view) {

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: RecyclerViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = RecyclerViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ProductAdapter { position -> navigateToProductDetail(position) }
        setupRecyclerView(adapter)
        observeProducts(adapter)
    }

    private fun observeProducts(adapter: ProductAdapter) {
        viewModel.allProducts.observe(viewLifecycleOwner, { list ->
            if (list.isEmpty()) {
                viewModel.getDataFromApi()
            } else {
                adapter.submitList(list)
            }
        })
    }

    private fun setupRecyclerView(adapter: ProductAdapter) {
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    fun navigateToProductDetail(position: Int) {
        viewModel.getProduct(position)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag, DetailFragment())
            .addToBackStack("LIST_FRAGMENT_TAG")
            .commit()
    }
}
