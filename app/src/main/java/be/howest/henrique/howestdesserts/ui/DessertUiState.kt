package be.howest.henrique.howestdesserts.ui

import be.howest.henrique.howestdesserts.data.Dessert

data class DessertUiState(
    val desserts: List<Dessert> = emptyList()
)