package be.howest.henrique.howestdesserts.ui

import be.howest.henrique.howestdesserts.data.Dessert

sealed interface DessertUiState {
    data object Loading : DessertUiState
    data class Success(val desserts: List<Dessert>) : DessertUiState
    data class Error(val message: String) : DessertUiState
}