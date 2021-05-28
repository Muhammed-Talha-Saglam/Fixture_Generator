package dev.bytecode.fixturegenerator.views.pages


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team

@ExperimentalComposeUiApi
@Composable
fun FixturePage(viewModel: DatabaseViewModel, navController: NavHostController) {

    val state = viewModel.fixtures.observeAsState()
    val fixtures = remember { state }
    val teams = viewModel.teams.value
    val size = fixtures.value?.size


    Scaffold(
        topBar = { MakeNewFixtureButton(viewModel, navController) },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {


            fixtures.value?.let {
                itemsIndexed(fixtures.value!!) { index, fixture ->
                    MakeFixturesListItem(index, size!!, fixture, viewModel, teams)

                }
            }


            item {
                Spacer(modifier = Modifier.height(100.dp))
            }

        }
    }

}


@Composable
fun MakeNewFixtureButton(viewModel: DatabaseViewModel, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.generateNewFixture()
                viewModel.refreshTable()
                navController.navigate("Teams")

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = Color.Yellow
            )
        ) {
            Text(text = "New Fixture")
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MakeFixturesListItem(
    index: Int,
    fixtureSize: Int,
    fixture: Fixture,
    viewModel: DatabaseViewModel,
    teams: List<Team>?
) {


    val week = index % (fixtureSize / 2) + 1
    val half = if (index < (fixtureSize / 2)) "First" else "Second"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Yellow)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "$half half",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Red
        )
        Text(
            text = "Week $week",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Color.Blue
        )

        fixture.matches.forEach { match ->
            val homeScore = remember {
                mutableStateOf(
                    match.homeScore.toString()
                )
            }

            val awayScore = remember {
                mutableStateOf(
                    match.awayScore.toString()

                )
            }
            Row(
                modifier = Modifier.fillMaxSize().padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = "${match.home}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign =TextAlign.Right,
                    modifier = Modifier.padding(end = 10.dp).weight(1f)

                )


                    val isPlayed = remember {
                        mutableStateOf(match.isPlayed)
                    }

                    val keyboardController = LocalSoftwareKeyboardController.current

                    TextField(
                        value = if (homeScore.value == "null") "" else homeScore.value,
                        onValueChange = {
                            homeScore.value = it
                        },
                        maxLines = 1,
                        modifier = Modifier
                            .size(50.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                        }),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.White,
                            textColor = Color.Black

                        ),
                        enabled = !isPlayed.value
                    )


                    if (isPlayed.value) {
                        Text(
                            text = "-",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(5.dp)
                        )
                    } else {
                        IconButton(
                            onClick = {


                                val hScore = homeScore.value.toIntOrNull()
                                val aScore = awayScore.value.toIntOrNull()


                                fixture.matches.find {
                                    it.home == match.home
                                }.also {
                                    it?.let {
                                        it.homeScore = hScore
                                        it.awayScore = aScore
                                        it.isPlayed = true
                                    }
                                }

                                val homeTeam = teams?.find { team ->
                                    team.name == match.home
                                }

                                val awayTeam = teams?.find { team ->
                                    team.name == match.away
                                }


                                if (hScore != null && aScore != null) {

                                    Log.d("fffff", "$hScore  -  $aScore")

                                    homeTeam!!.played += 1
                                    awayTeam!!.played += 1
                                    Log.d("fffff", homeTeam?.played.toString())
                                    Log.d("fffff", awayTeam?.played.toString())

                                    when {
                                        hScore > aScore -> {
                                            Log.d("when", "inside when")


                                            homeTeam!!.win += 1
                                            awayTeam!!.loss += 1

                                            Log.d("when", "${homeTeam?.win} - ${awayTeam?.loss}")
                                        }
                                        hScore < aScore -> {
                                            awayTeam!!.win += 1
                                            homeTeam!!.loss += 1
                                        }
                                        else -> {
                                            homeTeam!!.draw += 1
                                            awayTeam!!.draw += 1
                                            Log.d(
                                                "when else",
                                                "${homeTeam?.draw} - ${awayTeam?.draw}"
                                            )

                                        }
                                    }
                                    homeTeam?.let { viewModel.updateTeam(it) }
                                    awayTeam?.let { viewModel.updateTeam(it) }


                                    viewModel.updateFixture(fixture)
                                    isPlayed.value = true
                                    keyboardController?.hide()

                                }



                            }) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Check",
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }



                    TextField(
                        value = if (awayScore.value == "null") "" else awayScore.value,
                        onValueChange = {
                            awayScore.value = it
                        },
                        maxLines = 1,
                        modifier = Modifier
                            .size(50.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                        }),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.White,
                            textColor = Color.Black
                        ),
                        enabled = !isPlayed.value

                    )



                Text(
                    text = "${match.away}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start =  10.dp).weight(1f)
                )

            }








        }


    }
}