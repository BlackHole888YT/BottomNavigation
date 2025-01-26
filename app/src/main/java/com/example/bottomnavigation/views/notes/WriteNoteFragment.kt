package com.example.bottomnavigation.views.notes

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.App
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.R
import com.example.bottomnavigation.data.model.NoteEntity
import com.example.bottomnavigation.databinding.PopupWindowBinding
import com.example.bottomnavigation.databinding.FragmentWriteNoteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
    private lateinit var binding: FragmentWriteNoteBinding
    private lateinit var popBinding: PopupWindowBinding
    var isBtnChangeColorActive:Boolean = false
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteNoteBinding.inflate(layoutInflater)
        popBinding = PopupWindowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteDate.setText(date)
        setListener()
    }

    private fun doNonActive(){
        isBtnChangeColorActive = false
        binding.btnSetColor.setBackgroundResource(R.drawable.btn_set_color)
    }

    private fun showPopupWindow(anchorView: View) {
        val popupWindow = PopupWindow(
            popBinding.root,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        // при нажатии на табличку с цветом
        popBinding.apply {
            setOrangeColor.setOnClickListener {
                colorId = 0
                Toast.makeText(requireContext(), R.string.set_orange_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
            setYellowColor.setOnClickListener {
                colorId = 1
                Toast.makeText(requireContext(), R.string.set_yellow_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
            setPurpleColor.setOnClickListener {
                colorId = 2
                Toast.makeText(requireContext(), R.string.set_purple_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
            setLightBlueColor.setOnClickListener {
                colorId = 3
                Toast.makeText(requireContext(), R.string.set_light_blue_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
            setGreenColor.setOnClickListener {
                colorId = 4
                Toast.makeText(requireContext(), R.string.set_green_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
            setPinkColor.setOnClickListener {
                colorId = 5
                Toast.makeText(requireContext(), R.string.set_red_color, Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
                doNonActive()

            }
        }
        popupWindow.setOnDismissListener {
            doNonActive()
        }
        popupWindow.elevation = 8f
        popupWindow.showAsDropDown(anchorView, -50, 50)
    }

    private fun setListener() {
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        if (pref.isGuest == true){
            binding.apply {
                val args = WriteNoteFragmentArgs.fromBundle(requireArguments())
                App.appDatabase?.noteDao()?.getNoteById(args.note)?.let {
                    noteEtName.setText(it.title)
                    noteEtNote.setText(it.description)
                    isEdit = true
                }

                //кнопка смены цвета - гость
                /**
                 * Красный - 0
                 * Желтый - 1
                 * Фиолетовый - 2
                 * Оранжевый - 3
                 * Зеленый - 4
                 * Голубой - 5
                 */
                btnSetColor.setOnClickListener {
                    if (isBtnChangeColorActive == false){
                        isBtnChangeColorActive = true
                        btnSetColor.setBackgroundResource(R.drawable.btn_set_color_active)
                        // при активации кнопки
                        showPopupWindow(btnSetColor)

                    }else{
                        isBtnChangeColorActive = false
                        btnSetColor.setBackgroundResource(R.drawable.btn_set_color)
                        // при ДЕактивации кнопки

                    }
                }

                //кнопка Готово для сохранения заметки - гость
                btnDone.setOnClickListener{
                    val noteEtName: String = binding.noteEtName.text.toString()
                    val noteEtText: String = binding.noteEtNote.text.toString()
                    val note = NoteEntity(noteEtName, noteEtText, date, colorId)
                    App.appDatabase?.noteDao()?.update(note)
                    if (isEdit){
                        val args = WriteNoteFragmentArgs.fromBundle(requireArguments())
                        note.id = args.note
                        App.appDatabase?.noteDao()?.update(note)
                        findNavController().navigateUp()
                    }else {
                        App.appDatabase?.noteDao()?.insert(note)
                        findNavController().navigateUp()
                    }
                }
                //кнопка Назад для выхода без сохранения - гость
                binding.noteBtnSave.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }else{
            binding.apply {
                //кнопка Назад для выхода без сохранения - гугл
                noteBtnSave.setOnClickListener {
                    findNavController().navigateUp()
                }

                //кнопка смены цвета - гугл
                btnSetColor.setOnClickListener {
                    if (isBtnChangeColorActive == false) {
                        isBtnChangeColorActive = true
                        btnSetColor.setBackgroundResource(R.drawable.btn_set_color_active)
                        // при активации кнопки
                        showPopupWindow(btnSetColor)
                    } else {
                        isBtnChangeColorActive = false
                        btnSetColor.setBackgroundResource(R.drawable.btn_set_color)
                        // при ДЕактивации кнопки

                    }
                }

                //кнопка Готово для сохранения - гугл
                btnDone.setOnClickListener {
                    val db = Firebase.firestore
                    val note = hashMapOf(
                        "title" to binding.noteEtName.text.toString(),
                        "description" to binding.noteEtNote.text.toString(),
                        "date" to date,
                    )

                    db.collection("notes")
                        .add(note)
                        .addOnSuccessListener { documentReference ->
                            findNavController().navigateUp()
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                            Toast.makeText(
                                requireContext(),
                                R.string.error_please_try_again,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    } companion object{
        const val TAG = "bh"
    }
}