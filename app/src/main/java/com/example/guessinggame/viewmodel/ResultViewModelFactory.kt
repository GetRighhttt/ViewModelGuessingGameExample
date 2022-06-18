package com.example.guessinggame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

// Factory implements the interface
// finalResult is the constructor needed
class ResultViewModelFactory(private val finalResult: String): ViewModelProvider.Factory {
    /**
     * The sole purpose of the factory class is to create and initialize viewModels
     * that whose constructors require arguments.
     * Ex: ResultViewModelFactory(private val finalResult: String)..
     * A Factory creates a NEW instance of an object each time it is called.
     */

    // this overridden is used to create viewModel objects
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Now we are going to check and make sure the viewModel is the correct type..
        if(modelClass.isAssignableFrom(ResultViewModelFactory::class.java))
            return ResultViewModelFactory(finalResult) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }

}