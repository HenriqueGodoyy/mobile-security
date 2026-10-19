package be.howest.henrique.howestdesserts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.howest.henrique.howestdesserts.data.Dessert
import be.howest.henrique.howestdesserts.data.DessertData
import be.howest.henrique.howestdesserts.ui.components.DessertCard
import be.howest.henrique.howestdesserts.ui.theme.HowestDessertsTheme

@Composable
fun DessertListScreen(
    desserts: List<Dessert>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(desserts, key = { it.id }) { dessert ->
            DessertCard(dessert = dessert)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DessertListScreenPreview() {
    HowestDessertsTheme {
        DessertListScreen(desserts = DessertData.desserts)
    }
}