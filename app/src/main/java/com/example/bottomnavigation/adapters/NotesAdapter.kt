package com.example.bottomnavigation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.ItemNoteBinding
import com.example.bottomnavigation.extension.OnItemClick

class NotesAdapter(
    private val onCustomClick: OnItemClick,
    private var colorIdc: Int

): ListAdapter<NoteEntity,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    val noteDrawables = listOf(
        R.drawable.item_note_0,
        R.drawable.item_note_1,
        R.drawable.item_note_2,
        R.drawable.item_note_3,
        R.drawable.item_note_4,
        R.drawable.item_note_5
    )

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(note: NoteEntity) {
            binding.pinkTvDate.text = note.date
            binding.pinkTvDesc.text = note.description
            binding.pinkTvTitle.text = note.title
            colorIdc = note.colorId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))

        when(colorIdc){
            0 ->{
                holder.itemView.setBackgroundResource(noteDrawables[0])
            }
            1 ->{
                holder.itemView.setBackgroundResource(noteDrawables[1])
            }
            2 ->{
                holder.itemView.setBackgroundResource(noteDrawables[2])
            }
            3 ->{
                holder.itemView.setBackgroundResource(noteDrawables[3])
            }
            4 ->{
                holder.itemView.setBackgroundResource(noteDrawables[4])
            }
            5 ->{
                holder.itemView.setBackgroundResource(noteDrawables[5])
            }
        }

        holder.itemView.setOnClickListener{
            onCustomClick.onClick(getItem(position), position)
        }
        holder.itemView.setOnLongClickListener {
            onCustomClick.onLongClick(getItem(position), position)
            true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.title == newItem.title
        }
    }
}