package com.example.guessinggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModels incorporate the separation of concerns idea for clean code.
 * ViewModels are used to store the business logic -> logic not used for updating UI elements.
 * They also save the configuration state of the app so when the configuration changes,
 * it gets updated with the viewModel. We need one viewModel per each UIcontroller.
 * A UIcontroller is an activity or a fragment.
 * ViewModels make code more readable, less error-prone, and simplifies the code.
 */

// This is the viewModel that we will use to hold the business logic for the game
// Nothing here is going to be change the UI.
class GameViewModel: ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment")
    private val secretWord = words.random().uppercase()
    private var correctGuesses = ""

    // assigning a livedata value to secretWordDisplay
    private val _secretWordDisplay = MutableLiveData<String>("")
    val secretWordDisplay: LiveData<String> get() = _secretWordDisplay

    // assigning a livedata value to incorrectGuesses
    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String> get() = _incorrectGuesses

    // assigning a livedata value to livesleft
    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft: LiveData<Int> get() = _livesLeft

    // assigning a livedata value to gameOver
    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean> get() = _gameOver

    // runs when the class is initialized
    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    // how the message should be displayed on the screen
    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    // returns the string if it appears in the secret word
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    // this gets called when the user makes a guess
    fun makeGuess(guess: String) {
        if(guess.length ==1) {
            if(secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if (isWon() || isLost()) {
                _gameOver.value = true
            }
        }
    }

    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    private fun isLost() = livesLeft.value ?: 0 <= 0

    // generates a string when the game is over
    fun wonLostMessage(): String {
        var message = " "
        if(isWon()) {
            message = "You won!"
        } else if(isLost()) {
            message = "You Lost!"
        }
        message += " The word was $secretWord ..."
        return message
    }

}