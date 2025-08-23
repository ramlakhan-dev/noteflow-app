package com.rl.noteflow.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rl.noteflow.R
import com.rl.noteflow.data.local.NoteDatabase
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import com.rl.noteflow.databinding.FragmentSearchBinding
import com.rl.noteflow.ui.adapters.NoteAdapter
import com.rl.noteflow.ui.viewmodel.NoteSharedViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteSharedViewModel: NoteSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val searchFactory = SearchViewModelFactory(noteRepository)
        searchViewModel = ViewModelProvider(this, searchFactory)[SearchViewModel::class]

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        noteAdapter = NoteAdapter{}
        binding.recyclerViewSearch.adapter = noteAdapter

        noteSharedViewModel = ViewModelProvider(requireActivity())[NoteSharedViewModel::class]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNotesData()

        binding.iBtnBackSearch.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etTextSearch.text.toString().trim()
                searchViewModel.searchNotes(query)

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(binding.etTextSearch.windowToken, 0)
                true
            } else {
                false
            }
        }
        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                noteSharedViewModel.selectNote(note)
                findNavController().navigate(R.id.action_searchFragment_to_noteDetailFragment)
            }


            override fun onItemLongClick(note: Note) {
                showDeleteNoteDialog(note)
            }
        })
    }

    private fun showDeleteNoteDialog(note: Note) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { dialog, _ ->
                searchViewModel.deleteNote(note)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun observeNotesData() {
        searchViewModel.notes.observe(viewLifecycleOwner) { notes ->
            if (notes.isEmpty()) {
                binding.tvNoNotes.visibility = View.VISIBLE
                binding.recyclerViewSearch.visibility = View.GONE
            } else {
                binding.tvNoNotes.visibility = View.GONE
                binding.recyclerViewSearch.visibility = View.VISIBLE
                noteAdapter.updateList(notes)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}