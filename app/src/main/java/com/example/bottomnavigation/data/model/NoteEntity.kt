package com.example.bottomnavigation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    val title: String = " ",
    val description: String? = null,
    val date: String? = null,
    var colorId: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}