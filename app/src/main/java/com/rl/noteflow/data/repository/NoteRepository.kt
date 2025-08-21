package com.rl.noteflow.data.repository

import com.rl.noteflow.data.local.dao.NoteDao
import com.rl.noteflow.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }
}