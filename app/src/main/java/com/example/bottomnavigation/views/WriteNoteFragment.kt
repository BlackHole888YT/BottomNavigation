package com.example.bottomnavigation.views

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.App
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.FragmentWriteNoteBinding
/// Открывается при создании новой заметки
class WriteNoteFragment : Fragment() {

    var colorId: Int = 0
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH) + 1
    var day = c.get(Calendar.DAY_OF_MONTH)
    var hour = c.get(Calendar.HOUR_OF_DAY)
    var minute = c.get(Calendar.MINUTE)
    @SuppressLint("DefaultLocale")
    var date: String = String.format("%02d.%02d.%d-%02d:%02d", day, month, year, hour, minute)
    val adapter = NotesAdapter()
    private lateinit var binding: FragmentWriteNoteBinding
    var isBtnChangeColorActive:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteDate.setText(date)
        setListener()
    }

    private fun setListener() {
        binding.apply {
            btnSetColor.setOnClickListener {
                if (isBtnChangeColorActive == false){
                    isBtnChangeColorActive = true
                    btnSetColor.setBackgroundResource(R.drawable.btn_set_color_active)
                }else{
                    isBtnChangeColorActive = false
                    btnSetColor.setBackgroundResource(R.drawable.btn_set_color)
                }
            }
            noteBtnSave.setOnClickListener{
                adapter.notifyItemChanged(0)
                val noteEtName: String = binding.noteEtName.text.toString()
                val noteEtText: String = binding.noteEtNote.text.toString()
                App.appDatabase?.noteDao()?.insert(NoteEntity(noteEtName, noteEtText, date, colorId))
                findNavController().navigateUp()
            }
        }
    }
}