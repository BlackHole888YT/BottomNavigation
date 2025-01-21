package com.example.bottomnavigation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentOnBoardBinding
/// Экран приветствия (показывается при первом запуске)
class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val onBoardPosition = requireArguments().getInt(ARG_ONBOARD_POSITION)
        when(onBoardPosition){
            0 -> {
                binding.onboardTvTitle.text = getString(R.string.str1_title)
                binding.onboardTvDescription.text = getString(R.string.str1_desc)
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_first)
            }
            1 -> {
                binding.onboardTvTitle.text = getString(R.string.str2_title)
                binding.onboardTvDescription.text = getString(R.string.str2_desc)
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_second)
            }
            2 -> {
                binding.onboardTvTitle.text = getString(R.string.str3_title)
                binding.onboardTvDescription.text = getString(R.string.str3_desc)
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_third)
            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}