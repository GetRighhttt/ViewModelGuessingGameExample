package com.example.guessinggame.viewmodel

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    // runs when the class is initialized
    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    // how the message should be displayed on the screen
    fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    // returns the string if it appears in the secret word
    fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    // this gets called when the user makes a guess
    fun makeGuess(guess: String) {
        if(guess.length ==1) {
            if(secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay, true)

    fun isLost() = livesLeft <= 0

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