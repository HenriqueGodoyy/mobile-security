package be.howest.henrique.howestdesserts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.henrique.howestdesserts.DessertApplication
import be.howest.henrique.howestdesserts.data.DessertRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DessertViewModel(
    private val dessertRepository: DessertRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DessertUiState>(DessertUiState.Loading)
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    init {
        loadDesserts()
    }

    fun loadDesserts() {
        viewModelScope.launch {
            _uiState.value = DessertUiState.Loading
            _uiState.value = try {
                DessertUiState.Success(dessertRepository.getDesserts())
            } catch (e: IOException) {
                DessertUiState.Error("Could not reach the server.\nIs the Flask API running?")
            } catch (e: HttpException) {
                DessertUiState.Error("Server error (HTTP ${e.code()}).")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DessertApplication
                DessertViewModel(application.container.dessertRepository)
            }
        }
    }
}