package com.rl.noteflow.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository

class SearchViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    fun searchNotes(query: String) {
        noteRepository.searchNotes(query).observeForever { result ->
            _notes.value = result
        }
    }
}

class SearchViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(noteRepository) as T
    }
}