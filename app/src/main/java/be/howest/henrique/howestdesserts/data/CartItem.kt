package be.howest.henrique.howestdesserts.data


data class CartItem(
    val dessertId: Int,
    val productName: String,
    val imageFile: String,
    val price: Double,
    val quantity: Int
)