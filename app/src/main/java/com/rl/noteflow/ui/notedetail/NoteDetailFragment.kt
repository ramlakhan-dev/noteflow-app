package com.rl.noteflow.ui.notedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rl.noteflow.databinding.FragmentNoteDetailBinding
import com.rl.noteflow.ui.viewmodel.NoteSharedViewModel
import java.text.DateFormat

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteSharedViewModel: NoteSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        noteSharedViewModel = ViewModelProvider(requireActivity())[NoteSharedViewModel::class]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iBtnBackDetail.setOnClickListener {
            findNavController().navigateUp()
        }
        observeNoteData()
    }

    private fun observeNoteData() {
        noteSharedViewModel.selectedNote.observe(viewLifecycleOwner) { note ->
            binding.tvTitle.text = note.title
            binding.tvDescription.text = note.description
            binding.tvTimeStamp.text = DateFormat.getDateTimeInstance().format(note.timeStamp)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}