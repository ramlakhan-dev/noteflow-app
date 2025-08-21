package com.rl.noteflow.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val allNotes = noteRepository.allNotes

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun markFavorite(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }
}

class HomeViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(noteRepository) as T
    }
}