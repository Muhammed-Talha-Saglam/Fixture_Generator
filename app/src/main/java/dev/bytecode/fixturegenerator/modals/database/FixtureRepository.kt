package dev.bytecode.fixturegenerator.modals.database

import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team
import kotlinx.coroutines.flow.Flow

class FixtureRepository(private val fixtureDao: FixtureDao) {

    suspend fun insertNewFixture(fixture: Fixture) {
        fixtureDao.insertFixture(fixture)
    }

    suspend fun clearFixture() {
        fixtureDao.clearFixture()
    }


    suspend fun addTeam(team: Team) {
        fixtureDao.insertTeam(team)
    }

    suspend fun deleteTeam(team: Team) {
        fixtureDao.deleteTeam(team)
    }

    suspend fun clearTeams() {
        fixtureDao.clearTeams()
    }

    suspend fun updateDatabase(fixtures: List<Fixture>){
        fixtureDao.updateFixture(fixtures)
    }

    val teamList: Flow<List<Team>> =  fixtureDao.getTeams()

    val fixtures = fixtureDao.getFixture()


}