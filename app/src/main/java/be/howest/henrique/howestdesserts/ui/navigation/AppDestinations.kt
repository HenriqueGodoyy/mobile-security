package be.howest.henrique.howestdesserts.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


object Routes {
    const val DESSERTS_GRAPH = "desserts"
    const val DESSERT_LIST = "dessert_list"

    const val DESSERT_ID_ARG = "dessertId"
    const val DESSERT_DETAIL = "dessert_detail/{$DESSERT_ID_ARG}"
    fun dessertDetail(dessertId: Int) = "dessert_detail/$dessertId"

    const val CART = "cart"
}

enum class TopLevelDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    DESSERTS(Routes.DESSERTS_GRAPH, "Desserts", Icons.AutoMirrored.Filled.List),
    CART(Routes.CART, "Cart", Icons.Filled.ShoppingCart)
}