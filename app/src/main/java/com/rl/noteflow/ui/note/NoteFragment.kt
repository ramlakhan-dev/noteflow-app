package com.rl.noteflow.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rl.noteflow.data.local.NoteDatabase
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import com.rl.noteflow.databinding.FragmentNoteBinding
import com.rl.noteflow.ui.viewmodel.NoteSharedViewModel

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteSharedViewModel: NoteSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val noteFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, noteFactory)[NoteViewModel::class]

        noteSharedViewModel = ViewModelProvider(requireActivity())[NoteSharedViewModel::class]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNoteData()
        binding.iBtnBackNote.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.iBtnSaveNote.setOnClickListener {
            val title = binding.etTitleNote.text.toString().trim()
            val description = binding.etDescriptionNote.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val note = noteSharedViewModel.selectedNote.value
                if (note != null) {
                    val updatedNote = note.copy(title = title, description = description)
                    noteViewModel.updateNote(updatedNote)
                    noteSharedViewModel.selectNote(updatedNote)

                } else {
                    noteViewModel.addNote(Note(title = title, description = description))
                }
                findNavController().navigateUp()
            }
        }
    }

    private fun observeNoteData() {
        noteSharedViewModel.selectedNote.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                binding.etTitleNote.setText(note.title)
                binding.etDescriptionNote.setText(note.description)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}