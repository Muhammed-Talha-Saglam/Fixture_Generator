package dev.bytecode.fixturegenerator.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.bytecode.fixturegenerator.views.pages.Page
import kotlin.math.roundToInt

@Composable
fun MakeBottomNavBar(
    navController: NavController,
    pages: List<Page>,
    bottomBarHeight: Dp,
    bottomBarOffsetHeightPx: MutableState<Float>
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Row(
        modifier = Modifier
            .height(bottomBarHeight)
            .fillMaxWidth()
            .offset { IntOffset(0, -bottomBarOffsetHeightPx.value.roundToInt()) }
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
            )
            .border(
                2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
            )
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        pages.forEach { screen ->

            val logo = when (screen.route) {
                "Teams" -> "Teams"
                "Fixture" -> "Fixture"
                else -> "Table"

            }

            val isSelected = currentRoute == screen.route



            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected) Color.Yellow else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable(
                        onClick = {



                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationRoute!!) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }


                        },
                    ),

                ) {
                Text(
                    logo,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Center),
                )
            }

        }
    }

}