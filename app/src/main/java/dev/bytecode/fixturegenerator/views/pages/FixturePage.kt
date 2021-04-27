package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel

@Composable
fun FixturePage(viewModel: DatabaseViewModel) {

    val state = viewModel.fixtures.observeAsState()
    val fixtures = remember { state }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            fixtures.value?.let {

                items(it) { fixture ->
                    Column(
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(5.dp)
                            )
                    ) {
                        fixture.matches.forEach { match ->
                            Text(text = "${match.home} - ${match.away}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { viewModel.generateNewFixture() },
        ) {
            Text(text = "New Fixture")
        }


    }

}