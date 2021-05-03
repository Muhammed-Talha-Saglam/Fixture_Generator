package dev.bytecode.fixturegenerator.modals

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "team")
data class Team(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    var played: Int = 0,
    var win: Int =0,
    var loss: Int =0,
    var draw: Int = 0

    )