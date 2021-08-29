package com.hsjiang.lifecycle.compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<Boolean>(false)
    val uiState: StateFlow<Boolean> = _uiState
}