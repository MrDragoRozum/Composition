package com.rozum.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rozum.composition.R
import com.rozum.composition.databinding.FragmentGameBinding
import com.rozum.composition.domain.entity.GameResult
import com.rozum.composition.domain.entity.GameSettings
import com.rozum.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

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
        binding.textViewOption1.setOnClickListener {
            launchGameFinishedFragment(
                GameResult(
                    true,
                    1,
                    2,
                    GameSettings(
                        1,
                        2,
                        3,
                        4)))
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
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

        private const val KEY_LEVEL = "level"
        const val NAME = "GameFragment"
        fun newInstance(level: Level) = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_LEVEL, level)
            }
        }
    }
}