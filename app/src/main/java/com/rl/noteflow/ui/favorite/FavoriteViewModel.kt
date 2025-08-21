package com.rl.noteflow.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rl.noteflow.data.repository.NoteRepository

class FavoriteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val allFavoriteNotes = noteRepository.allFavoriteNotes
}

class FavoriteViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(noteRepository) as T
    }
}