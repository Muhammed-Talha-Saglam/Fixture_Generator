package dev.bytecode.fixturegenerator.modals

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "team")
data class Team(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,

    )