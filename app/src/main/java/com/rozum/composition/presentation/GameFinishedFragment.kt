package com.rozum.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.rozum.composition.R
import com.rozum.composition.databinding.FragmentGameFinishedBinding
import com.rozum.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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
        with(binding) {
            imageViewEmojiResult.setImageResource(getSmileResId())
            textViewRequiredAnswers.text = getString(
                R.string.required_score,
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            textViewRequiredPercentage.text = getString(
                R.string.required_percentage,
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
            textViewScoreAnswers.text = getString(
                R.string.score_answers,
                gameResult.countOfRightAnswers.toString()
            )
            textViewScorePercentage.text = getString(
                R.string.score_percentage,
                gameResult.getPercentOfRightAnswer.toString()
            )
        }
    }

    private fun getSmileResId() = if (gameResult.winner) {
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

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    companion object {

        const val KEY_GAME_RESULT = "key_gameResult"
        fun newInstance(gameResult: GameResult) = GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_GAME_RESULT, gameResult)
            }
        }
    }
}