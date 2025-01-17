package com.example.bottomnavigation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.util.Calendar
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.ItemNoteBinding

class NotesAdapter: ListAdapter<NoteEntity,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(note: NoteEntity) {

            binding.pinkTvDate.text = note.date
            binding.pinkTvDesc.text = note.description
            binding.pinkTvTitle.text = note.title

            when(note.colorId){
                0 -> {
                    binding.pinkCardView.setCardBackgroundColor(Color.DKGRAY)
                }
                1 ->{
                    binding.pinkCardView.setCardBackgroundColor(Color.RED)
                }
                2 ->{
                    binding.pinkCardView.setCardBackgroundColor(Color.YELLOW)
                }
                3 ->{
                    binding.pinkCardView.setCardBackgroundColor(Color.BLUE)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
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