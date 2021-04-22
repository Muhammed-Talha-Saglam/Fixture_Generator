package dev.bytecode.fixturegenerator.modals

import androidx.room.Entity


@Entity
data class Match(
    val home: String?,
    val away: String?
    )