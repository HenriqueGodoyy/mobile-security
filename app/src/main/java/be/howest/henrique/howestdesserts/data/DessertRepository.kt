package be.howest.henrique.howestdesserts.data

import be.howest.henrique.howestdesserts.data.network.DessertApiService
import be.howest.henrique.howestdesserts.data.network.toDessert


class DessertRepository(
    private val dessertApiService: DessertApiService
) {
    suspend fun getDesserts(): List<Dessert> =
        dessertApiService.getDesserts().map { it.toDessert() }

    suspend fun getDessert(id: Int): Dessert =
        dessertApiService.getDessert(id).toDessert()
}