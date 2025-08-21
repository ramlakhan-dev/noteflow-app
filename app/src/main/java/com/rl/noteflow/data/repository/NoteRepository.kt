package com.rl.noteflow.data.repository

import androidx.lifecycle.LiveData
import com.rl.noteflow.data.local.dao.NoteDao
import com.rl.noteflow.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    val allFavoriteNotes: LiveData<List<Note>> = noteDao.getAllFavoriteNotes()

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}