package be.howest.henrique.howestdesserts.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.howest.henrique.howestdesserts.data.CartItem


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val dessertId: Int,
    val productName: String,
    val imageFile: String,
    val price: Double,
    val quantity: Int
)

fun CartItemEntity.toCartItem(): CartItem = CartItem(
    dessertId = dessertId,
    productName = productName,
    imageFile = imageFile,
    price = price,
    quantity = quantity
)