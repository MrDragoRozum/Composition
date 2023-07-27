package com.rozum.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rozum.composition.databinding.FragmentChooseLevelBinding
import com.rozum.composition.domain.entity.Level
import com.rozum.composition.domain.entity.Level.EASY
import com.rozum.composition.domain.entity.Level.HARD
import com.rozum.composition.domain.entity.Level.NORMAL
import com.rozum.composition.domain.entity.Level.TEST


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ChooseLevelFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners() {
        with(binding) {
            buttonLevelTest.setOnClickListener { launchGameFragment(TEST) }
            buttonLevelEasy.setOnClickListener { launchGameFragment(EASY) }
            buttonLevelNormal.setOnClickListener { launchGameFragment(NORMAL) }
            buttonLevelHard.setOnClickListener { launchGameFragment(HARD) }
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController()
            .navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}