package com.rozum.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rozum.composition.R
import com.rozum.composition.databinding.FragmentChooseLevelBinding
import com.rozum.composition.domain.entity.Level.*
import com.rozum.composition.domain.entity.Level


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
        findNavController().navigate(
            R.id.action_chooseLevelFragment_to_gameFragment,
            Bundle().apply {
                putParcelable(GameFragment.KEY_LEVEL, level)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        //        const val NAME = "ChooseLevelFragment"
        fun newInstance() = ChooseLevelFragment()
    }
}