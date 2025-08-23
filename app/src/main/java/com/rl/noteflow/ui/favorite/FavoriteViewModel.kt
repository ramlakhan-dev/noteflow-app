package com.rl.noteflow.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val allFavoriteNotes = noteRepository.allFavoriteNotes

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}

class FavoriteViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(noteRepository) as T
    }
}