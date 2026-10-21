package be.howest.henrique.howestdesserts.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.henrique.howestdesserts.ui.components.ErrorScreen
import be.howest.henrique.howestdesserts.ui.components.LoadingScreen

@Composable
fun DessertRoute(
    modifier: Modifier = Modifier,
    viewModel: DessertViewModel = viewModel(factory = DessertViewModel.Factory)
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        DessertUiState.Loading -> LoadingScreen(modifier)
        is DessertUiState.Success -> DessertListScreen(
            desserts = state.desserts,
            modifier = modifier
        )

        is DessertUiState.Error -> ErrorScreen(
            message = state.message,
            onRetry = viewModel::loadDesserts,
            modifier = modifier
        )
    }
}