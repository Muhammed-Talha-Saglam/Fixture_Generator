package dev.bytecode.fixturegenerator.modals.database

import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team
import kotlinx.coroutines.flow.Flow

class FixtureRepository(private val fixtureDao: FixtureDao) {

    suspend fun insertNewFixture(fixture: Fixture) {
        fixtureDao.insertFixture(fixture)
    }

    suspend fun clearDatabase() {
        fixtureDao.clearDatabase()
    }

    suspend fun updateDatabase(fixtures: List<Fixture>){
        fixtureDao.updateFixture(fixtures)
    }

    val fixtures = fixtureDao.getFixture()

//    suspend fun getFixture(): List<Fixture> {
//        return fixtureDao.getFixture()
//    }

    val teamList: Flow<List<Team>> =  fixtureDao.getTeams()


    suspend fun addTeam(team: Team) {
        fixtureDao.insertTeam(team)
    }
}