package com.merttoptas.coroutinestutorial

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by merttoptas on 22.10.2022.
 */
class MainViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            one()
            two()
        }
    }

    fun start() {}

    private fun one() {
        Log.d("deneme1", "One viewModel")
    }

    private suspend fun two() {
        Log.d("deneme1", "Two viewModel")
    }
}