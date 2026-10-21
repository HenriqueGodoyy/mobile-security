package be.howest.henrique.howestdesserts.ui.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.henrique.howestdesserts.DessertApplication
import be.howest.henrique.howestdesserts.data.CartItem
import be.howest.henrique.howestdesserts.data.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartRepository.getCartItems()
        .map { items ->
            CartUiState(
                items = items,
                subtotal = items.sumOf { it.price * it.quantity }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState()
        )

    fun removeItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.removeItem(item.dessertId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DessertApplication
                CartViewModel(application.container.cartRepository)
            }
        }
    }
}