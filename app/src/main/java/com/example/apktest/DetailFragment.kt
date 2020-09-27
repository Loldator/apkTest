package com.example.apktest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apktest.databinding.DetailViewBinding
import kotlinx.android.synthetic.main.detail_view.*

class DetailFragment : Fragment(R.layout.detail_view) {

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: DetailViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DetailViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeProduct()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeProduct() {
        viewModel.productDetail.observe(
            viewLifecycleOwner,
            { details ->
                toolbar.title = details.name
                toolbar.subtitle = details.name2
                product_price.text = details.price
                product_litre_price.text = details.litrePrice
                product_apk.text = details.apk
                product_volume.text = details.volume
                product_percent.text = details.percent
                product_type.text = details.type
                product_group.text = details.productGroup
            },
        )
    }
}
