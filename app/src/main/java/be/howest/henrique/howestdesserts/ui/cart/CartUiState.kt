package be.howest.henrique.howestdesserts.ui.card

import be.howest.henrique.howestdesserts.data.CartItem


data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0
)