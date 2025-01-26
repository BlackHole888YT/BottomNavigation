package com.example.bottomnavigation.views.notes

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.App
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapters.NotesAdapter
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.FragmentNotesBinding
import com.example.bottomnavigation.extension.OnItemClick
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.util.Util
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment(), OnItemClick{

    var isLinear: Boolean = true
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapterA: NotesAdapter
    private var notesList: ArrayList<NoteEntity> = arrayListOf()
    private var colorId: Int = 0

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
        adapterA = NotesAdapter(this, colorId)
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
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        if (pref.isGuest) {
            App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { model ->
                notesList.addAll(model)
                adapterA.submitList(notesList)
                adapterA.notifyDataSetChanged()
            }
        }else{
            val db = Firebase.firestore
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    notesList.clear()
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val title = document.data["title"].toString()
                        val description = document.data["description"].toString()
                        val date = document.data["date"].toString()
                        val note = NoteEntity(title, description, date, 0)
                        notesList.add(note)
                        adapterA.submitList(notesList)
                        adapterA.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }

    private fun filteredNotes(query: String) {
        val filteredList = notesList.filter { note ->
            note.title.contains(query, ignoreCase = true) || note.title.contains(query, ignoreCase = true)
        }
        adapterA.submitList(filteredList)
    }

    override fun onClick(noteEntity: NoteEntity, position: Int) {
        findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToWriteNoteFragment(noteEntity.id))
    }

    override fun onLongClick(noteEntity: NoteEntity, position: Int) {
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val db = Firebase.firestore
        builder
            .setTitle(R.string.warning)
            .setMessage(R.string.duwannadeleteit)
            .setPositiveButton(R.string.delete) { dialog, which ->
                if (pref.isGuest){
                    App.appDatabase?.noteDao()?.delete(noteEntity)
                }else{
                    // Получает айди документа, на который нажал
                    // position - позиция документа, а именно заметки
                    db.collection("notes")
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            for ((index, document) in querySnapshot.withIndex()) {
                                if (index == position) {
                                    val docId = document.id
                                    Log.d("bhF", "Document ID number $position: ${docId}")
                                    // удаление документа
                                    db.collection("notes").document(docId)
                                        .delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(requireContext(), "DocumentSnapshot with id: $docId successfully deleted!", Toast.LENGTH_SHORT).show()
                                            Log.d(TAG_FIRESTORE, "DocumentSnapshot with id: $docId successfully deleted!")
                                        }
                                        .addOnFailureListener {
                                            e -> Log.w(TAG_FIRESTORE, "Error deleting document with id: $docId", e)
                                            Toast.makeText(requireContext(), "Error deleting document with id: $docId", Toast.LENGTH_SHORT).show()
                                        }
                                    // удаление документа
                                    // удаление заметки есть, но список заметок остается не изменным до перезапуска приложения

                                }
                            }

                        }
                        .addOnFailureListener { exception ->
                            Log.w("TAG_FIRESTORE", "Error getting documents", exception)
                        }
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.cancel()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object{
        const val TAG = "bh"
        const val TAG_FIRESTORE = "bhf"
    }
}