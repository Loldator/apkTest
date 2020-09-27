package com.example.apktest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apktest.databinding.ItemViewBinding
import com.example.apktest.db.Product

class ProductAdapter(val onClick: (Int) -> Unit) : ListAdapter<Product,
        ProductAdapter.ProductViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewholder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewholder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewholder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onClick(adapterPosition) }
        }

        fun bind(item: Product) = with(itemView) {
            binding.let {
                it.productName.text = item.name
                if (item.name2.isNotEmpty()) it.productName2.text = item.name2
                it.productPrice.text = context.getString(R.string.priceTemplate, item.price)
                it.productApk.text = context.getString(R.string.apkTemplate, item.apk)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
