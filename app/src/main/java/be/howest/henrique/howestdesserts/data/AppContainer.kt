package be.howest.henrique.howestdesserts.data

import be.howest.henrique.howestdesserts.data.network.DessertApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

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
}
