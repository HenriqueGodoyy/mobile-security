package be.howest.henrique.howestdesserts.ui

import androidx.lifecycle.ViewModel
import be.howest.henrique.howestdesserts.data.DessertData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DessertViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(
        DessertUiState(desserts = DessertData.desserts)
    )

    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()
}