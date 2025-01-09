package com.example.bottomnavigation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.databinding.FragmentWriteNoteBinding
import com.example.bottomnavigation.extension.setBackStackData
import com.example.bottomnavigation.models.Notes
import java.util.ArrayList


class WriteNoteFragment : Fragment() {
    var number: Int = 0
    val adapter = NotesAdapter(ArrayList())
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

        setListener()
    }

    private fun setListener() {
        binding.apply {
            noteBtnSave.setOnClickListener{
                number = 0
                adapter.notifyItemChanged(0)
                val not = Notes(binding.noteEtNote.text.toString(), binding.noteEtName.text.toString(), number)
                setBackStackData("DESK", not, true)

                val pref = PreferenceHelper()
                pref.unit(requireContext())
                pref.text = not.toString()
            }
        }
    }


}