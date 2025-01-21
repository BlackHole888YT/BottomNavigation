package com.example.bottomnavigation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.util.Calendar
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.ItemNoteBinding
import com.google.android.material.transition.Hold

class NotesAdapter: ListAdapter<NoteEntity,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    val noteDrawables = listOf(
        R.drawable.item_note_1,
        R.drawable.item_note_2,
        R.drawable.item_note_3,
        R.drawable.item_note_4,
        R.drawable.item_note_5,
        R.drawable.item_note_6
    )

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(note: NoteEntity) {

            binding.pinkTvDate.text = note.date
            binding.pinkTvDesc.text = note.description
            binding.pinkTvTitle.text = note.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
        //val ne: NoteEntity = getItem(position) а оно надо?
        val backgroundIndex = position % noteDrawables.size
        holder.itemView.setBackgroundResource(noteDrawables[backgroundIndex])
        //holder.bind(ne) а оно надо?
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