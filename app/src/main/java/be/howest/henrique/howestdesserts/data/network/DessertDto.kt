package be.howest.henrique.howestdesserts.data.network

import be.howest.henrique.howestdesserts.data.Dessert
import com.google.gson.annotations.SerializedName


data class DessertDto(
    @SerializedName("id") val id: Int,
    @SerializedName("productName") val productName: String,
    @SerializedName("imageFile") val imageFile: String,
    @SerializedName("description") val description: String,
    @SerializedName("size") val size: Int,
    @SerializedName("price") val price: Double
)

fun DessertDto.toDessert(): Dessert = Dessert(
    id = id,
    productName = productName,
    imageFile = imageFile,
    description = description,
    size = size,
    price = price
)