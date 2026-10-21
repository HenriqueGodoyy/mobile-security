package be.howest.henrique.howestdesserts.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.henrique.howestdesserts.ui.components.ErrorScreen
import be.howest.henrique.howestdesserts.ui.components.LoadingScreen


@Composable
fun DessertDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DessertDetailViewModel = viewModel(factory = DessertDetailViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (val state = uiState) {
        DessertDetailUiState.Loading -> LoadingScreen(modifier)
        is DessertDetailUiState.Error -> ErrorScreen(
            message = state.message,
            onRetry = viewModel::loadDessert,
            modifier = modifier
        )
        is DessertDetailUiState.Success -> DessertDetailScreen(
            dessert = state.dessert,
            onAddToCart = {
                viewModel.addToCart(state.dessert)
                Toast.makeText(context, "${state.dessert.productName} added to cart", Toast.LENGTH_SHORT).show()
            },
            modifier = modifier
        )
    }
}