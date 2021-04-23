package dev.bytecode.fixturegenerator.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import dev.bytecode.fixturegenerator.views.pages.Page

@Composable
fun MakeBottomNavBar(
    navController: NavController,
    pages: List<Page>
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)


    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
            )
            .border(
                2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
            )
            .padding(horizontal = 30.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pages.forEach { screen ->

            val logo = when(screen.route) {
                "Teams" -> Icons.Default.List
                else -> Icons.Default.Home

            }

            var isSelected = currentRoute == screen.route



            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected) Color.Black else Color.White,
                        shape = CircleShape
                    )
                    .padding(10.dp)
                    .clickable(
                        onClick = {

                            if (currentRoute != screen.route) {

                                navController.navigate(screen.route ) {



                                }

                            }

                        },
                    ),

                ) {
                Icon(
                    logo,
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                    tint = if (isSelected) Color.White else Color.Black
                )
            }

        }
    }

}