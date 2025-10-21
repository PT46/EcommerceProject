package com.example.ecommerce.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.databinding.FragmentProductBinding
import com.example.ecommerce.ui.dashboard.ProductAdapter

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private var categoryId: String? = null
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString("category_id")
            categoryName = it.getString("category_name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCategoryTitle.text = categoryName

        binding.recyclerViewProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        // Temporary placeholder
        val adapter = ProductAdapter(emptyList())
        binding.recyclerViewProducts.adapter = adapter
    }
}
