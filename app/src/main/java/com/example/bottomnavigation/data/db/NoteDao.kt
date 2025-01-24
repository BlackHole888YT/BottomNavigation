package com.example.bottomnavigation.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bottomnavigation.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: NoteEntity)

    @Update
    fun update(notes: NoteEntity)

    @Delete
    fun delete(notes: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity
}
