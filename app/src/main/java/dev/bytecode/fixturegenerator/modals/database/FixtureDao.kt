package dev.bytecode.fixturegenerator.modals.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("SELECT * FROM team")
    fun getTeams(): Flow<List<Team>>

    @Query("DELETE FROM fixture")
    suspend fun clearDatabase()

    @Transaction
    suspend fun updateFixture(fixtures: List<Fixture>) {
        clearDatabase()

        fixtures.forEach {
            insertFixture(it)
        }

    }


}