package com.example.bottomnavigation.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bottomnavigation.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<NoteEntity>>

    @Insert
    fun insertAll(vararg notes: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: NoteEntity)

}
