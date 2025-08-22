package com.rl.noteflow.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rl.noteflow.data.model.Note

class NoteSharedViewModel: ViewModel() {
    private val _selectedNote = MutableLiveData<Note?>()
    val selectedNote: LiveData<Note?> get() = _selectedNote

    fun selectNote(note: Note?) {
        _selectedNote.value = note
    }
}