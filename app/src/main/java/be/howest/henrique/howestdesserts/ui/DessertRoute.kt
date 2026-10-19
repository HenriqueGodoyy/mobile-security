package be.howest.henrique.howestdesserts.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DessertRoute(
    modifier: Modifier = Modifier,
    viewModel: DessertViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DessertListScreen(
        desserts = uiState.desserts,
        modifier = modifier
    )
}