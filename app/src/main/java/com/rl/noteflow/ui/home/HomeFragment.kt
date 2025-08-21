package com.rl.noteflow.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rl.noteflow.R
import com.rl.noteflow.data.local.NoteDatabase
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.data.repository.NoteRepository
import com.rl.noteflow.databinding.FragmentHomeBinding
import com.rl.noteflow.ui.adapters.NoteAdapter
import com.rl.noteflow.ui.viewmodel.NoteSharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteSharedViewModel: NoteSharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val homeFactory = HomeViewModelFactory(noteRepository)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class]

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        noteAdapter = NoteAdapter(homeViewModel)
        binding.recyclerView.adapter = noteAdapter

        noteSharedViewModel = ViewModelProvider(requireActivity())[NoteSharedViewModel::class]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNotesData()
        binding.fabBtnAdd.setOnClickListener {
            noteSharedViewModel.selectNote(null)
            findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
        }

        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                noteSharedViewModel.selectNote(note)
                findNavController().navigate(R.id.action_homeFragment_to_noteDetailFragment)
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
                homeViewModel.deleteNote(note)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun observeNotesData() {
        homeViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            if (notes.isEmpty()) {
                binding.tvNoNotes.visibility = View.VISIBLE
            } else {
                binding.tvNoNotes.visibility = View.GONE
                noteAdapter.updateList(notes)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}