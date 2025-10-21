package com.example.ecommerce.data.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_id") val id: String,
    @SerializedName("product_name") val name: String,
    @SerializedName("product_image_url") val imageUrl: String,
    @SerializedName("product_price") val price: String,
    @SerializedName("product_description") val description: String
)

