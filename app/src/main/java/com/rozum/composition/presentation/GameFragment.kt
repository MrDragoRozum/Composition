package com.rozum.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rozum.composition.R
import com.rozum.composition.databinding.FragmentGameBinding
import com.rozum.composition.domain.entity.GameResult
import com.rozum.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val options by lazy {
        mutableListOf(
            binding.textViewOption1,
            binding.textViewOption2,
            binding.textViewOption3,
            binding.textViewOption4,
            binding.textViewOption5,
            binding.textViewOption6
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgumentLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        observes()
    }

    private fun observes() {
        with(viewModel) {
            with(binding) {
                formattedTime.observe(viewLifecycleOwner) {
                    textViewTimer.text = it
                }
                question.observe(viewLifecycleOwner) {
                    for ((index, element) in it.options.withIndex()) {
                        options[index].text = element.toString()
                    }
                    textViewSum.text = it.sum.toString()
                    textViewLeftNumber.text = it.visibleNumber.toString()
                }
                progressAnswer.observe(viewLifecycleOwner) {
                    textViewAnswersProgress.text = it
                }
                percentOfRightAnswer.observe(viewLifecycleOwner) {
                    progressBar.setProgress(it, true)
                }
                enoughPercent.observe(viewLifecycleOwner) {
                    progressBar.progressTintList = ColorStateList.valueOf(getColor(it))
                }
                enoughCount.observe(viewLifecycleOwner) {
                    textViewAnswersProgress.setTextColor(getColor(it))
                }
                minPercent.observe(viewLifecycleOwner) {
                    progressBar.secondaryProgress = it
                }
                gameResult.observe(viewLifecycleOwner) {
                    launchGameFinishedFragment(it)
                }
            }
        }
    }

    private fun getColor(isColor: Boolean) = if (isColor) ContextCompat.getColor(
        requireContext(),
        android.R.color.holo_green_light
    )
    else ContextCompat.getColor(
        requireContext(),
        android.R.color.holo_red_light
    )

    private fun listeners() {
        for (textView in options) {
            textView.setOnClickListener {
                val answer = textView.text.toString().toInt()
                viewModel.chooseAnswer(answer)
            }
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            R.id.action_gameFragment_to_gameFinishedFragment,
            Bundle().apply {
                putParcelable(GameFinishedFragment.KEY_GAME_RESULT, gameResult)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgumentLevel() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val KEY_LEVEL = "level"
        const val NAME = "GameFragment"
        fun newInstance(level: Level) = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_LEVEL, level)
            }
        }
    }
}