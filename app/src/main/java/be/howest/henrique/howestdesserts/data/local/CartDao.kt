package be.howest.henrique.howestdesserts.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY productName ASC")
    fun getAllItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE dessertId = :dessertId")
    suspend fun getItem(dessertId: Int): CartItemEntity?

    @Upsert
    suspend fun upsert(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE dessertId = :dessertId")
    suspend fun deleteById(dessertId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun deleteAll()
}