package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.modals.Team

@Composable
fun TeamListPage(viewModel: DatabaseViewModel, navController: NavController) {


    val state = viewModel.teams.observeAsState()
    val teams = remember { state }

    Column(
        modifier = Modifier.scrollable(rememberScrollState(),orientation = Orientation.Vertical)
    ) {
        teams.value?.forEach {
            Text(text = it.name)
        }

        Row() {

            val input = remember { mutableStateOf("") }


            OutlinedButton(
                onClick = {
                    if (
                        input.value.isNotBlank()
                    ) {
                        viewModel.addNewTeam(Team(name = input.value))
                    }
                }
            ) {
                Text(text = "ADD TEAM")
            }

            OutlinedTextField(
                input.value,
                onValueChange = {
                    input.value = it
                }
            )


        }

        OutlinedButton(
            onClick = { viewModel.deleteAllTeams() }) {
            Text(text = "Delete Teams")
        }

    }

}