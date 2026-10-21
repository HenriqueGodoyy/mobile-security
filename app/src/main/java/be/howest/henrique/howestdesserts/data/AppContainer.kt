package be.howest.henrique.howestdesserts.data

import android.content.Context
import androidx.room.Room
import be.howest.henrique.howestdesserts.data.local.CartDatabase
import be.howest.henrique.howestdesserts.data.network.DessertApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AppContainer(context: Context) {


    private val baseUrl = "http://10.0.2.2:5000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val dessertApiService: DessertApiService by lazy {
        retrofit.create(DessertApiService::class.java)
    }

    val dessertRepository: DessertRepository by lazy {
        DessertRepository(dessertApiService)
    }

    private val cartDatabase: CartDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }

    val cartRepository: CartRepository by lazy {
        CartRepository(cartDatabase.cartDao())
    }
}