package be.howest.henrique.howestdesserts.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.henrique.howestdesserts.DessertApplication
import be.howest.henrique.howestdesserts.data.CartRepository
import be.howest.henrique.howestdesserts.data.Dessert
import be.howest.henrique.howestdesserts.data.DessertRepository
import be.howest.henrique.howestdesserts.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DessertDetailUiState {
    data object Loading : DessertDetailUiState
    data class Success(val dessert: Dessert) : DessertDetailUiState
    data class Error(val message: String) : DessertDetailUiState
}


class DessertDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val dessertRepository: DessertRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val dessertId: Int = checkNotNull(savedStateHandle[Routes.DESSERT_ID_ARG])

    private val _uiState = MutableStateFlow<DessertDetailUiState>(DessertDetailUiState.Loading)
    val uiState: StateFlow<DessertDetailUiState> = _uiState.asStateFlow()

    init {
        loadDessert()
    }

    fun loadDessert() {
        viewModelScope.launch {
            _uiState.value = DessertDetailUiState.Loading
            _uiState.value = try {
                DessertDetailUiState.Success(dessertRepository.getDessert(dessertId))
            } catch (e: IOException) {
                DessertDetailUiState.Error("Could not reach the server.\nIs the Flask API running?")
            } catch (e: HttpException) {
                DessertDetailUiState.Error("Server error (HTTP ${e.code()}).")
            }
        }
    }

    fun addToCart(dessert: Dessert) {
        viewModelScope.launch {
            cartRepository.addDessert(dessert)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DessertApplication
                DessertDetailViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    dessertRepository = application.container.dessertRepository,
                    cartRepository = application.container.cartRepository
                )
            }
        }
    }
}