package com.rl.noteflow.ui.adapters

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rl.noteflow.data.model.Note
import com.rl.noteflow.databinding.LayoutEachNoteBinding

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<Note> = emptyList()
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding = LayoutEachNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = notes[position]

        holder.binding.apply {
            tvNoteTitle.text = note.title
            tvNoteDescription.text = limitWords(note.description, 30)
            tvNoteTimeStamp.text = DateFormat.getDateTimeInstance().format(note.timeStamp)
        }

        holder.binding.root.setOnClickListener {
            listener?.onItemClick(note)
        }

        holder.binding.root.setOnLongClickListener {
            listener?.onItemLongClick(note)
            true
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(val binding: LayoutEachNoteBinding): RecyclerView.ViewHolder(binding.root)


    fun limitWords(text: String, maxWords: Int): String {
        val words = text.split("\\s+".toRegex())
        return if (words.size <= maxWords) {
            text
        } else {
            words.take(maxWords).joinToString(" ") + "..."
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)

        fun onItemLongClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}