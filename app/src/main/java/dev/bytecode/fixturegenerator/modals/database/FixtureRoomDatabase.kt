package dev.bytecode.fixturegenerator.modals.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.bytecode.fixturegenerator.modals.Fixture
import dev.bytecode.fixturegenerator.modals.Match
import dev.bytecode.fixturegenerator.modals.Team
import dev.bytecode.fixturegenerator.modals.database.converters.Converters

@Database(entities = [Fixture::class, Team::class, Match::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FixtureRoomDatabase: RoomDatabase() {

    abstract fun fixtureDao(): FixtureDao

    companion object {

        @Volatile
        private var INSTANCE: FixtureRoomDatabase? = null

        fun getDatabase(context: Context): FixtureRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FixtureRoomDatabase::class.java,
                    "fixture_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }

}