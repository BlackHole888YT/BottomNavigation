package com.example.bottomnavigation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.databinding.FragmentNotesBinding
import com.example.bottomnavigation.extension.getBackStackData
import com.example.bottomnavigation.models.Notes


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NotesAdapter
    private val notes: ArrayList<Notes> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initialize()
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToWriteNoteFragment())
        }
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        pref.text?.let { Notes(it, it, 0) }?.let { notes.add(it) }
    }



    private fun initialize() {
        adapter = NotesAdapter(notes)
        binding.rvNotes.adapter = adapter
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getData(){
        getBackStackData<Notes>("DESK"){
                receivedNote ->
            //notes.add(receivedNote)
            adapter.submitList(notes)
        }

    }


}