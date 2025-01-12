package com.example.bottomnavigation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentOnBoardBinding


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
                binding.onboardTvTitle.text = "Удобство"
                binding.onboardTvDescription.text = "Создавайте заметки в два клика!\nЗаписывайте мысли, идеи и\nважные задачи мгновенно."
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_first)


            }
            1 -> {
                binding.onboardTvTitle.text = "Организация"
                binding.onboardTvDescription.text = "Организуйте заметки по папкам\nи тегам. Легко находите нужную\nинформацию в любое время."
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_second)
            }
            2 -> {
                binding.onboardTvTitle.text = "Синхронизация"
                binding.onboardTvDescription.text = "Синхронизация на всех\nустройствах. Доступ к записям в\nлюбое время и в любом месте."
                binding.onboardLottieAnim.setAnimation(R.raw.lottie_anim_third)

            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }

}