package be.howest.henrique.howestdesserts.data


data class Dessert(
    val id: Int,
    val productName: String,
    val imageFile: String,
    val description: String,
    val size: Int,
    val price: Double
)