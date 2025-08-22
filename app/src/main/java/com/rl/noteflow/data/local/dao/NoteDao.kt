package com.rl.noteflow.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rl.noteflow.data.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY timeStamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE isFavorite = 1 ORDER BY timeStamp DESC")
    fun getAllFavoriteNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query ORDER BY timeStamp DESC")
    fun searchNotes(query: String): LiveData<List<Note>>


    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}