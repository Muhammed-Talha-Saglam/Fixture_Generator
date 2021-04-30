package dev.bytecode.fixturegenerator.modals

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "match")
data class Match  (


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val home: String? = null,

    var homeScore: Int? = null,

    val away: String? = null,

    var awayScore: Int? = null,

    ) {

    override fun equals(other: Any?): Boolean {
        return (other as Match).id == this.id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (home?.hashCode() ?: 0)
        result = 31 * result + (homeScore ?: 0)
        result = 31 * result + (away?.hashCode() ?: 0)
        result = 31 * result + (awayScore ?: 0)
        return result
    }
}
