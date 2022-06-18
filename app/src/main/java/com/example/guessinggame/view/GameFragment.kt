package com.example.guessinggame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.R
import com.example.guessinggame.databinding.FragmentGameBinding
import com.example.guessinggame.viewmodel.GameViewModel

class GameFragment : Fragment() {

    // declare our binding variables
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    // define a property of viewModel
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set binding to inflate the fragment
        _binding = FragmentGameBinding.inflate(layoutInflater)
        val view = binding.root
        // tells the viewModel provider to get the viewModel object linked with the fragment
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        // call the update screen method
        updateScreen()

        // now we set the button to navigate to the other fragment
        binding.guessButton.setOnClickListener {
            // we use viewModel a number of times to reference the properties in the viewModel
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if(viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateScreen() {
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "You have ${viewModel.livesLeft} lives left"
        binding.incorrectGuesses.text = "Incorrect guess: ${viewModel.incorrectGuesses}"
    }

}