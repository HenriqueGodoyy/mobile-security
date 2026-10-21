package be.howest.henrique.howestdesserts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.howest.henrique.howestdesserts.data.Dessert
import be.howest.henrique.howestdesserts.ui.theme.HowestDessertsTheme
import coil3.compose.AsyncImage

/** Stateless and reusable: it only needs a Dessert and an add-to-cart event. */
@Composable
fun DessertDetailScreen(
    dessert: Dessert,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = dessert.imageFile,
            contentDescription = dessert.productName,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = dessert.productName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = dessert.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DessertInfoItem(label = "Size", value = "${dessert.size} g")
            DessertInfoItem(label = "Price", value = "€ %.2f".format(dessert.price))
        }

        Spacer(Modifier.height(24.dp))

        // Event up: the screen only reports the click, the ViewModel adds to the cart.
        Button(
            onClick = onAddToCart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Add to cart")
        }
    }
}

@Composable
private fun DessertInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DessertDetailScreenPreview() {
    HowestDessertsTheme {
        DessertDetailScreen(
            dessert = Dessert(
                id = 1,
                productName = "Cupcake",
                imageFile = "https://devicedev.vesper-design.be/assets/cupcake.png",
                description = "A delicious cupcake",
                size = 500,
                price = 3.5
            ),
            onAddToCart = {}
        )
    }
}