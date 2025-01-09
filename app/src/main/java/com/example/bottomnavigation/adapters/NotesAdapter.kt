package com.example.bottomnavigation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.databinding.ItemNoteBinding
import com.example.bottomnavigation.models.Notes

class NotesAdapter(private val notes: ArrayList<Notes>): ListAdapter<Notes,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    var col:Int = 0

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(note: Notes) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH) + 1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            binding.pinkTvDate.text = String.format("%02d.%02d.%d-%02d:%02d", month, day, year, hour, minute)
            binding.pinkTvDesc.text = note.description
            binding.pinkTvTitle.text = note.title


            while (true){
                if (col == 0){
                    binding.pinkCardView.setCardBackgroundColor(Color.parseColor("#E695B0")) //red
                    col += 1
                    break
                }else if (col == 1){
                    binding.pinkCardView.setCardBackgroundColor(Color.parseColor("#DFE695")) //yellow
                    col += 1
                    break
                }else if (col == 2){
                    binding.pinkCardView.setCardBackgroundColor(Color.parseColor("#9EE695")) //green
                    col += 1
                    break
                }else if (col == 3){
                    binding.pinkCardView.setCardBackgroundColor(Color.parseColor("#9EFFFF")) //light blue
                    col = 0
                    break
                }else{
                    col = 0
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])


    }


    class DiffCallback : DiffUtil.ItemCallback<Notes>(){
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.title == newItem.title
        }

    }

}