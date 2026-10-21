package be.howest.henrique.howestdesserts.data

import be.howest.henrique.howestdesserts.data.local.CartDao
import be.howest.henrique.howestdesserts.data.local.CartItemEntity
import be.howest.henrique.howestdesserts.data.local.toCartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CartRepository(
    private val cartDao: CartDao
) {
    fun getCartItems(): Flow<List<CartItem>> =
        cartDao.getAllItems().map { entities -> entities.map { it.toCartItem() } }

    suspend fun addDessert(dessert: Dessert) {
        val existing = cartDao.getItem(dessert.id)
        if (existing == null) {
            cartDao.upsert(
                CartItemEntity(
                    dessertId = dessert.id,
                    productName = dessert.productName,
                    imageFile = dessert.imageFile,
                    price = dessert.price,
                    quantity = 1
                )
            )
        } else {
            cartDao.upsert(existing.copy(quantity = existing.quantity + 1))
        }
    }

    suspend fun removeItem(dessertId: Int) {
        cartDao.deleteById(dessertId)
    }

    suspend fun clearCart() {
        cartDao.deleteAll()
    }
}