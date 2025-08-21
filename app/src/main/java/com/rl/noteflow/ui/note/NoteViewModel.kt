package com.rl.noteflow.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }
}

class NoteViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(noteRepository) as T
    }
}

