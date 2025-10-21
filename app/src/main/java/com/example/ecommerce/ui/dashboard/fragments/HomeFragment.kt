package com.example.ecommerce.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.data.model.Category
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.ui.dashboard.CategoryAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories()
    }

    private fun loadCategories() {

        val categoryList = listOf<Category>()

        val adapter = CategoryAdapter(categoryList) { selectedCategory ->
            val productFragment = ProductFragment().apply {
                arguments = Bundle().apply {
                    putString("category_id", selectedCategory.id)
                    putString("category_name", selectedCategory.name)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, productFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCategories.adapter = adapter
    }
}
