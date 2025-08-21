package com.rl.noteflow.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rl.noteflow.data.repository.NoteRepository

class HomeViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val allNotes = noteRepository.allNotes
}

class HomeViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(noteRepository) as T
    }
}