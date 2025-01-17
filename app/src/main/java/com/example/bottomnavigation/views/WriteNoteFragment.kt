package com.example.bottomnavigation.views

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.App
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.FragmentWriteNoteBinding
import java.util.ArrayList


class WriteNoteFragment : Fragment() {
    var colorId: Int = 0

    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH) + 1
    var day = c.get(Calendar.DAY_OF_MONTH)
    var hour = c.get(Calendar.HOUR_OF_DAY)
    var minute = c.get(Calendar.MINUTE)

    var date: String = String.format("%02d.%02d.%d-%02d:%02d", month, day, year, hour, minute)

    //var number: Int = 0
    val adapter = NotesAdapter()
    private lateinit var binding: FragmentWriteNoteBinding

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

    fun ex (noteEntity: NoteEntity){
        if(colorId == null){
            colorId = noteEntity.colorId
        }
    }


    private fun setListener() {
        binding.apply {
            noteBtnSave.setOnClickListener{
                //number = 0
                adapter.notifyItemChanged(0)
                val noteEtName: String = binding.noteEtName.text.toString()
                val noteEtText: String = binding.noteEtNote.text.toString()
                //val not = Notes(binding.noteEtNote.text.toString(), binding.noteEtName.text.toString(), number)
                //setBackStackData("DESK", not, true)

                App.appDatabase?.noteDao()?.insert(NoteEntity(noteEtName, noteEtText, date, colorId))

                findNavController().navigateUp()
                //val pref = PreferenceHelper()
                //pref.unit(requireContext())
                //pref.text = not.toString()

            }

        }

    }
}