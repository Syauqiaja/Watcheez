package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syauqi.watcheez.core.data.source.network.response.people.KnownForItem
import com.syauqi.watcheez.utils.enums.Gender

@Entity(tableName = "peoples")
data class PeopleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "peopleId")
    val id: Int,
    val name: String,
    val popularity: Double,
    val photoUrl: String,
    val gender: Int,
)