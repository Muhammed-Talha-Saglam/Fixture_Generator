package dev.bytecode.fixturegenerator

import android.app.Application
import dev.bytecode.fixturegenerator.modals.database.FixtureRepository
import dev.bytecode.fixturegenerator.modals.database.FixtureRoomDatabase

class BaseApplication : Application() {


    private val database by lazy { FixtureRoomDatabase.getDatabase(this) }

    val repository  by lazy { FixtureRepository(database.fixtureDao()) }


}