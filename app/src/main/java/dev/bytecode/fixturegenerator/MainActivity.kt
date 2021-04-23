package dev.bytecode.fixturegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModelFactory
import dev.bytecode.fixturegenerator.ui.theme.FixtureGeneratorTheme
import dev.bytecode.fixturegenerator.views.pages.HomePage

class MainActivity : ComponentActivity() {


    private val viewModel: DatabaseViewModel by viewModels {
        DatabaseViewModelFactory((application as BaseApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FixtureGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomePage(viewModel)
                }
            }
        }
    }
}


