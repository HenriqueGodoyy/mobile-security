package be.howest.henrique.howestdesserts.ui.cart

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.henrique.howestdesserts.data.CartItem
import be.howest.henrique.howestdesserts.ui.theme.HowestDessertsTheme
import coil3.compose.AsyncImage


@Composable
fun CartRoute(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CartScreen(
        uiState = uiState,
        onRemoveItem = viewModel::removeItem,
        onClearCart = viewModel::clearCart,
        onShareCart = { shareCart(context, uiState) },
        modifier = modifier
    )
}


@Composable
fun CartScreen(
    uiState: CartUiState,
    onRemoveItem: (CartItem) -> Unit,
    onClearCart: () -> Unit,
    onShareCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Title + Share action
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your Cart",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onShareCart, enabled = uiState.items.isNotEmpty()) {
                Icon(Icons.Filled.Share, contentDescription = "Share cart")
            }
        }

        if (uiState.items.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Your cart is empty", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.items, key = { it.dessertId }) { item ->
                    CartItemCard(item = item, onRemove = { onRemoveItem(item) })
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Subtotal:", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.weight(1f))
            Text(
                text = formatPrice(uiState.subtotal),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = onClearCart,
            enabled = uiState.items.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Clear Cart")
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageFile,
                contentDescription = item.productName,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${formatPrice(item.price)} x ${item.quantity}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove ${item.productName}",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

private fun formatPrice(price: Double): String = "€%.2f".format(price)

/** Plain-text summary of the cart. A pure function: easy to read and test. */
fun buildCartSummary(uiState: CartUiState): String = buildString {
    appendLine("My Howest Desserts cart:")
    uiState.items.forEach { item ->
        appendLine("- ${item.productName} x ${item.quantity}: ${formatPrice(item.price * item.quantity)}")
    }
    append("Subtotal: ${formatPrice(uiState.subtotal)}")
}

private fun shareCart(context: Context, uiState: CartUiState) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "My Howest Desserts cart")
        putExtra(Intent.EXTRA_TEXT, buildCartSummary(uiState))
    }
    context.startActivity(Intent.createChooser(sendIntent, "Share cart"))
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    HowestDessertsTheme {
        CartScreen(
            uiState = CartUiState(
                items = listOf(
                    CartItem(0, "Cupcake", "", 5.0, 1),
                    CartItem(3, "Froyo", "", 5.0, 1)
                ),
                subtotal = 10.0
            ),
            onRemoveItem = {},
            onClearCart = {},
            onShareCart = {}
        )
    }
}