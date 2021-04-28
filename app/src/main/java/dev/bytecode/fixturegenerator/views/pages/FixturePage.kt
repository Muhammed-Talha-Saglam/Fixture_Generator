package dev.bytecode.fixturegenerator.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bytecode.fixturegenerator.controllers.DatabaseViewModel

@Composable
fun FixturePage(viewModel: DatabaseViewModel) {

    val state = viewModel.fixtures.observeAsState()
    val fixtures = remember { state }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray.copy(0.4f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { viewModel.generateNewFixture() },
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

                itemsIndexed(fixtures) { index, fixture  ->


                    val week = index % (fixtures.size /2) +1
                    val half = if(index <(fixtures.size /2) ) "First" else "Second"

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Yellow),
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = "${match.home}")
                                Text(
                                    text = "-",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                    )
                                Text(text = "${match.away}")
                            }
                        }

                    }
                }
            }


            item {
                Spacer(modifier = Modifier.height(100.dp))
            }

        }




    }

}