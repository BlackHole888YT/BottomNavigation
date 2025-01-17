package com.example.bottomnavigation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigation.App
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.FragmentNotesBinding


class NotesFragment : Fragment() {

    var isLinear: Boolean = true
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapterA: NotesAdapter

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
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
                }else{
                    isLinear = true
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                }
            }
        }

    }

    private fun getData(){
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){model ->
            adapterA.submitList(model)
            adapterA.notifyDataSetChanged()
        }

    }

}