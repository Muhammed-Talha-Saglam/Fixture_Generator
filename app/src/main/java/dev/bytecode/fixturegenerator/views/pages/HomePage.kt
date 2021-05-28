package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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
    val scaffoldState = rememberScaffoldState()


    val bottomBarHeight = 70.dp
    val bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }


    // connection to the nested scroll system and listen to the scroll
    // happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        scaffoldState = scaffoldState,
        bottomBar = { MakeBottomNavBar(navController, pages, bottomBarHeight, bottomBarOffsetHeightPx) }
    ) {

        NavHost(navController = navController, startDestination = "Teams") {
            composable("Teams") { TeamListPage(viewModel = viewModel) }
            composable("Fixture") { FixturePage(viewModel, navController) }
            composable("Table") { TablePage(viewModel) }

        }

    }

}