package dev.bytecode.fixturegenerator.modals.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface FixtureDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFixture(fixture: Fixture)

    @Update(onConflict = REPLACE)
    suspend fun updateFixture(fixture: Fixture)

    @Query("SELECT * FROM fixture")
    fun getFixture(): Flow<List<Fixture>>

    @Insert
    suspend fun insertTeam(team: Team)

    @Update(onConflict = REPLACE)
    suspend fun updateTeam(team: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("SELECT * FROM team")
    fun getTeams(): Flow<List<Team>>

    @Query("DELETE FROM fixture")
    suspend fun clearFixture()

    @Query("DELETE FROM team")
    suspend fun clearTeams()


    @Transaction
    suspend fun updateAllFixtures(fixtures: List<Fixture>) {
        clearFixture()

        fixtures.forEach {
            insertFixture(it)
        }

    }


}