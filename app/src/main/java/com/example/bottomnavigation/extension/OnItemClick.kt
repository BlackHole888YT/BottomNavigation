package com.example.bottomnavigation.extension

import com.example.bottomnavigation.data.model.NoteEntity

interface OnItemClick {
    fun onClick(noteEntity: NoteEntity, position: Int)
    fun onLongClick(noteEntity: NoteEntity, position: Int)
}