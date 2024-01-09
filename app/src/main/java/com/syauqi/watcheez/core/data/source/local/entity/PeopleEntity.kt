package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syauqi.watcheez.utils.enums.Gender

@Entity(tableName = "peoples")
data class PeopleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val popularity: Double,
    val photoUrl: String,
    val gender: Int
)