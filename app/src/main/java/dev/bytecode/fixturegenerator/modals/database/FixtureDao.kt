package dev.bytecode.fixturegenerator.modals.database

import androidx.room.*
import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface FixtureDao {

    @Insert
    suspend fun insertFixture(fixture: Fixture)

    @Query("SELECT * FROM fixture")
    fun getFixture(): Flow<List<Fixture>>

    @Insert
    suspend fun insertTeam(team: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("SELECT * FROM team")
    fun getTeams(): Flow<List<Team>>

    @Query("DELETE FROM fixture")
    suspend fun clearFixture()

    @Query("DELETE FROM team")
    suspend fun clearTeams()


    @Transaction
    suspend fun updateFixture(fixtures: List<Fixture>) {
        clearFixture()

        fixtures.forEach {
            insertFixture(it)
        }

    }


}