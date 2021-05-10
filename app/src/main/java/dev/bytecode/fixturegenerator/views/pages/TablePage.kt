package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel

@Composable
fun TablePage(viewModel: DatabaseViewModel) {

    val state = viewModel.teams.observeAsState()
    val teams = remember { state }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
            .padding(horizontal = 5.dp, vertical = 20.dp)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
    ) {



        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Team ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
            Row {
                Text(text = "P ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(text = "M ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(text = "W ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(text = "D ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
                Text(text = "L ", color = Color.Red, fontWeight = FontWeight.Bold,fontSize = 18.sp)
            }
        }

        Divider()

        teams.value?.sortedDescending()?.forEachIndexed { index, team ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${index + 1}. ${team.name} ")
                Row {
                    Text(text = "${(team.win * 3) + team.draw } ", Modifier.size(18.dp), textAlign = TextAlign.Right)
                    Text(text = "${team.played} ", Modifier.size(18.dp), textAlign = TextAlign.Right)
                    Text(text = "${team.win} ", Modifier.size(18.dp), textAlign = TextAlign.Right)
                    Text(text = "${team.draw} ", Modifier.size(18.dp), textAlign = TextAlign.Right)
                    Text(text = "${team.loss} ", Modifier.size(18.dp), textAlign = TextAlign.Right)
                }
            }
            Divider()

        }

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Text(text = "P: points", fontSize = 10.sp)
            Text(text = "M: matches played", fontSize = 10.sp)
            Text(text = "W: win", fontSize = 10.sp)
            Text(text = "D: drawal", fontSize = 10.sp)
            Text(text = "L: loss", fontSize = 10.sp)

        }

        Spacer(modifier = Modifier.height(100.dp))
    }

}