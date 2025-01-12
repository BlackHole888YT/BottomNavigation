package com.example.bottomnavigation.views

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bottomnavigation.PreferenceHelper
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapters.OnBoardAdapter
import com.example.bottomnavigation.databinding.FragmentPagerItemBinding

class PagerItemFragment : Fragment() {

    private lateinit var binding: FragmentPagerItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
    }

    private fun setupListener() {
        binding.viewPager.adapter = OnBoardAdapter(this@PagerItemFragment)
    }

    private fun initialize() = with(binding.viewPager) {

        val gray: String = "#C8BEBE" //цвет светло серый
        val orange: String = "#FF6700" //цвет оранжевый

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position){ //это самая гениальная реализация точек для перелистывания, не ну а че
                    0 ->{
                        binding.dot1.setBackgroundColor(Color.parseColor(orange))
                        binding.dot2.setBackgroundColor(Color.parseColor(gray))
                        binding.dot3.setBackgroundColor(Color.parseColor(gray))
                    }
                    1 ->{
                        binding.dot2.setBackgroundColor(Color.parseColor(orange))

                        binding.dot1.setBackgroundColor(Color.parseColor(gray))
                        binding.dot3.setBackgroundColor(Color.parseColor(gray))
                    }
                    2 ->{
                        binding.dot3.setBackgroundColor(Color.parseColor(orange))

                        binding.dot1.setBackgroundColor(Color.parseColor(gray))
                        binding.dot2.setBackgroundColor(Color.parseColor(gray))
                    }
                }

                if (position == 2){
                    binding.btnOnboardTvSkip.apply {
                        visibility = View.GONE
                    }
                    binding.viewPagerBtn.apply {
                        visibility = View.VISIBLE
                        binding.viewPagerBtn.isClickable = true
                        text = "Начать"
                        setOnClickListener{
                            val pref = PreferenceHelper()
                            pref.unit(requireContext())
                            pref.isOnBoardShow = true
                            findNavController().navigate(PagerItemFragmentDirections.actionPagerItemFragmentToNotesFragment())
                        }
                    }
                }else{
                    binding.btnOnboardTvSkip.apply{
                        visibility = View.VISIBLE
                        setOnClickListener{
                            findNavController().navigate(PagerItemFragmentDirections.actionPagerItemFragmentToNotesFragment())
                        }

                    }
                    binding.viewPagerBtn.visibility = View.INVISIBLE
                    binding.viewPagerBtn.isClickable = false
                }
            }
        })
        binding.viewPager.setOnClickListener {
            if (currentItem < 1){
                setCurrentItem(currentItem + 1, true)
            }
        }
    }
}