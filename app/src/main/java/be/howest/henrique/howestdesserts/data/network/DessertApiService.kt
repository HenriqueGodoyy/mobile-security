package be.howest.henrique.howestdesserts.data.network

import retrofit2.http.GET
import retrofit2.http.Path
interface DessertApiService {

    @GET("desserts")
    suspend fun getDesserts(): List<DessertDto>

    @GET("desserts/{id}")
    suspend fun getDessert(@Path("id") id: Int): DessertDto
}