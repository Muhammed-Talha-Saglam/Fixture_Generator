package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.modals.Team

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun TeamListPage(viewModel: DatabaseViewModel, navController: NavController) {


    val state = viewModel.teams.observeAsState()
    val teams = remember { state }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondary)
            .padding(horizontal = 10.dp, vertical = 25.dp)

    ) {

        MakeAddTeamField(viewModel)


        Spacer(modifier = Modifier.height(20.dp))



        Text(
            text = "Delete Teams",
            color = Color.White,
            textDecoration = TextDecoration.Underline,
            fontSize = 20.sp,
            modifier = Modifier.clickable {
                viewModel.deleteAllTeams()
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            teams.value?.let { teams ->
                items(teams) { it ->
                    ListItem(
                        text = { Text(text = it.name, color = Color.White ) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.Magenta
                            )
                        },
                        trailing = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.clickable {
                                    viewModel.deleteTeam(it)
                                }
                            )
                        },
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .border(
                                color = Color.Yellow,
                                width = 3.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )

                }

            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

    }

}

@ExperimentalComposeUiApi
@Composable
fun MakeAddTeamField(viewModel: DatabaseViewModel) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        val input = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current



        TextField(
            input.value,
            onValueChange = {
                input.value = it
            },
            placeholder = {
                Text(text = "Team name...")
            },
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()

            }),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor =Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )


        IconButton(
            onClick = {
                if (
                    input.value.isNotBlank()
                ) {
                    viewModel.addNewTeam(Team(name = input.value))
                    input.value = ""

                }
            },
            modifier = Modifier.background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
        ) {
            Icon(Icons.Default.Add, null, tint = Color.White)
        }


    }
}