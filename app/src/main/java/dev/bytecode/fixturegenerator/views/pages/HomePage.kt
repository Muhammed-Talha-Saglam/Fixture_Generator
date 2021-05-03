package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.views.components.MakeBottomNavBar


sealed class Page(val route: String) {
    object Teams : Page("Teams")
    object Fixture : Page("Fixture")
    object Table : Page("Table")
}

val pages = listOf(
    Page.Teams,
    Page.Fixture,
    Page.Table
)

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun HomePage(viewModel: DatabaseViewModel) {


    val navController = rememberNavController()



    Scaffold(
        bottomBar = { MakeBottomNavBar(navController, pages) }
    ) {

        NavHost(navController = navController, startDestination = "Teams") {
            composable("Teams") { TeamListPage(viewModel = viewModel) }
            composable("Fixture") { FixturePage(viewModel) }
            composable("Table") { TablePage(viewModel) }

        }

    }

}