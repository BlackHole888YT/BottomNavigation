package com.example.bottomnavigation.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Query
import com.example.bottomnavigation.App
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    var isLinear: Boolean = true
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapterA: NotesAdapter
    private var notesList: List<NoteEntity> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        getData()
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToWriteNoteFragment())
        }
        val pref = PreferenceHelper()
        pref.unit(requireContext())
    }

    private fun initialize() {
        adapterA = NotesAdapter()
        binding.rvNotes.apply {
            adapter = adapterA
        }
        binding.changeLayout.setOnClickListener {
            adapterA.apply {
                if (isLinear){
                    isLinear = false
                    binding.changeLayout.setBackgroundResource(R.drawable.change_to_layout_2)
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
                }else{
                    isLinear = true
                    binding.changeLayout.setBackgroundResource(R.drawable.change_to_layout_1)
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                }
            }
        }
        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                filteredNotes(s.toString())
            }
            override fun afterTextChanged(s: Editable?){

            }
        })
    }

    private fun getData(){
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){model ->
            notesList = model
            adapterA.submitList(notesList)
            adapterA.notifyDataSetChanged()
        }
    }

    private fun filteredNotes(query: String) {
        val filteredList = notesList.filter { note ->
            note.title.contains(query, ignoreCase = true) || note.title.contains(query, ignoreCase = true)
        }
        adapterA.submitList(filteredList)
    }
}