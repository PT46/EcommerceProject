package com.example.ecommerce.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_id") val id: String,
    @SerializedName("category_name") val name: String,
    @SerializedName("category_image_url") val imageUrl: String
)
