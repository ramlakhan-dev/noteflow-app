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

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val noteFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, noteFactory)[NoteViewModel::class]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iBtnBackNote.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.iBtnSaveNote.setOnClickListener {
            val title = binding.etTitleNote.text.toString().trim()
            val description = binding.etDescriptionNote.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val note = Note(title = title, description = description)
                noteViewModel.addNote(note)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}