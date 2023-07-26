package com.rozum.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rozum.composition.R
import com.rozum.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {


    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null

    private val binding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener { retryGame() }
        installDataInViews()
    }

    private fun installDataInViews() {
        binding.gameResult = args.gameResult
        with(binding) {
            with(args) {
                imageViewEmojiResult.setImageResource(getSmileResId())
//                textViewRequiredAnswers.text = getString(
//                    R.string.required_score,
//                    gameResult.gameSettings.minCountOfRightAnswers.toString()
//                )
//                textViewRequiredPercentage.text = getString(
//                    R.string.required_percentage,
//                    gameResult.gameSettings.minPercentOfRightAnswers.toString()
//                )
//                textViewScoreAnswers.text = getString(
//                    R.string.score_answers,
//                    gameResult.countOfRightAnswers.toString()
//                )
                textViewScorePercentage.text = getString(
                    R.string.score_percentage,
                    gameResult.getPercentOfRightAnswer.toString()
                )
            }
        }
    }

    private fun getSmileResId() = if (args.gameResult.winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}