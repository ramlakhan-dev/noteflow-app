package com.rl.noteflow.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rl.noteflow.R
import com.rl.noteflow.data.local.NoteDatabase
import com.rl.noteflow.data.repository.NoteRepository
import com.rl.noteflow.databinding.FragmentFavoriteBinding
import com.rl.noteflow.ui.adapters.NoteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val favoriteFactory = FavoriteViewModelFactory(noteRepository)
        favoriteViewModel = ViewModelProvider(this, favoriteFactory)[FavoriteViewModel::class]

        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireContext())
        noteAdapter = NoteAdapter{}
        binding.recyclerViewFavorite.adapter = noteAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavoriteNotesData()
        binding.iBtnBackFavorite.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeFavoriteNotesData() {
        favoriteViewModel.allFavoriteNotes.observe(viewLifecycleOwner) { notes ->
            if (notes.isEmpty()) {
                binding.tvNoFavorites.visibility = View.VISIBLE
            } else {
                binding.tvNoFavorites.visibility = View.GONE
                noteAdapter.updateList(notes)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}