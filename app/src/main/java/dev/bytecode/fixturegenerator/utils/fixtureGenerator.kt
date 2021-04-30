package dev.bytecode.fixturegenerator.utils

import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Match
import dev.bytecode.fixturegenerator.modals.Team

fun generateFixture(teamsGiven: List<Team>) : MutableList<Fixture> {

    val firstHalf = mutableListOf<MutableList<Match>>()
    val secondHalf = mutableListOf<MutableList<Match>>()

    // We shuffle the list so that we get a different fixture every time
    var teams = teamsGiven.shuffled().toMutableList()

    // If team size is odd number, we make it even for the sake of algorithm
    val tempTeam = Team(name = "Temp")
    if (teams.size % 2 == 1) {
        teams.add(tempTeam)
    }

    val weeksNumber = teams.size - 1

    val matchNumberPerWeek = teams.size / 2

    var i = 0
    while (i < weeksNumber) {

        val weeklyGame = mutableListOf<Match>()
        val weeklySecondHalf = mutableListOf<Match>()

        var j = 0
        while (j < matchNumberPerWeek) {

            val homeIndex = j
            val awayIndex = (teams.size - 1) - j


            val home = teams.get(homeIndex)
            val away = teams.get(awayIndex)

            if (home.name != "Temp" && away.name != "Temp") {

                weeklyGame.add(
                    Match(home= home.name, away= away.name)
                )

                weeklySecondHalf.add(
                    Match( home = away.name, away = home.name)
                )
            }
            j++

        }
        firstHalf.add(weeklyGame)
        secondHalf.add(weeklySecondHalf)

        val newList = mutableListOf<Team>()

        newList.add(teams.get(0))
        newList.add(teams.get(teams.size - 1))


        for (k in 1 until teams.size - 1) {
            newList.add(teams[k])
        }

        teams = newList

        i++
    }

    val totalList = (firstHalf + secondHalf)

    val fixtures = mutableListOf<Fixture>()

    totalList.forEach {
        val fixture = Fixture()
        fixture.matches.addAll(it)

        fixtures.add(fixture)
    }

    return fixtures
}