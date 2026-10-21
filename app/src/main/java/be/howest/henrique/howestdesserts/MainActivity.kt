package be.howest.henrique.howestdesserts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import be.howest.henrique.howestdesserts.ui.navigation.HowestDessertsApp
import be.howest.henrique.howestdesserts.ui.theme.HowestDessertsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HowestDessertsTheme {
                HowestDessertsApp()
            }
        }
    }
}