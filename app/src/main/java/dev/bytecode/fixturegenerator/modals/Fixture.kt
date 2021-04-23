package dev.bytecode.fixturegenerator.modals

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixture")
data class Fixture (

    @PrimaryKey
    //@Embedded(prefix = "matches_")
    val matches: MutableList<Match> = mutableListOf()

){
    constructor() : this( mutableListOf())
}
