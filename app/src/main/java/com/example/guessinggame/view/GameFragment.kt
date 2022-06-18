package com.example.guessinggame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        // GameFragment ask the ViewModelProvider for an instance of GameViewModel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        /**
         * Here is an example of LiveData implementation.
         * LiveData tells the fragment or activity when the properties have changed.
         * I.E. the data has been updated.
         * observe() - used to make a fragment respond to value changes in the viewModel's
         * MutableLiveData property.
         * viewLifecycleOwner - refers to the lifecycle of the fragment's views
         * Observer - class that receives live data, that's only active when it the fragment
         * has access to its views.
         */

        // Live Data update to the incorrect guesses with a new value
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect Guesses: $newValue"
        })
        // LiveData update to the number of lives left afterwards
        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "You have $newValue lives left!"
        })

        // LiveData update to the display of the word.
        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })

        // Incorporate livedata into the fragment
        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if(newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        // now we set the button to navigate to the other fragment
        binding.guessButton.setOnClickListener {
            // we use viewModel a number of times to reference the properties in the viewModel
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}