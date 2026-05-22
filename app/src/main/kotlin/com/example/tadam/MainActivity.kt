package com.example.tadam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tadam.ui.navigation.TadamApp
import com.example.tadam.ui.theme.TadamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TadamTheme {
                TadamApp()
            }
        }
    }
}
