package dev.bytecode.fixturegenerator.views.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel
import dev.bytecode.fixturegenerator.modals.Fixture


@ExperimentalComposeUiApi
@Composable
fun FixturePage(viewModel: DatabaseViewModel) {

    val state = viewModel.fixtures.observeAsState()
    val fixtures = remember { state }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { viewModel.generateNewFixture() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = Color.Yellow
                )
        ) {
            Text(text = "New Fixture")
        }

        Spacer(modifier = Modifier.height(25.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {

            fixtures.value?.let { fixtures ->

                itemsIndexed(fixtures) { index, fixture ->

                    MakeFixturesListItem(index, fixtures.size, fixture, viewModel)


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
fun MakeFixturesListItem(
    index: Int,
    fixtureSize: Int,
    fixture: Fixture,
    viewModel: DatabaseViewModel
) {

    val week = index % (fixtureSize / 2) + 1
    val half = if (index < (fixtureSize / 2)) "First" else "Second"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Yellow)
            .padding(horizontal = 5.dp),
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

            Log.d("bugFix", match.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {

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
                Text(
                    text = "${match.home}",
                    modifier = Modifier.fillMaxWidth(0.25f),
                    textAlign = TextAlign.Right,
                    fontSize = 12.sp

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

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

                        )
                    )


                    Text(
                        text = "-",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

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
                        )
                    )


                }

                Text(
                    text = "${match.away}",
                    modifier = Modifier.fillMaxWidth(0.25f),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )

                IconButton(onClick = {

                    fixture.matches.find {
                        it.home == match.home
                    }.also {
                        it?.let {
                            it.homeScore = homeScore.value.toIntOrNull()
                            it.awayScore = awayScore.value.toIntOrNull()
                        }
                    }


                    viewModel.updateFixture(fixture)

                }) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Check",
                        tint = MaterialTheme.colors.primary
                    )
                }


            }
        }


    }
}