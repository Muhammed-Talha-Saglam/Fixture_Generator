package dev.bytecode.fixturegenerator.modals

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixture")
data class Fixture (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var matches: MutableList<Match> = mutableListOf()

){
    constructor() : this(0, mutableListOf())
}
