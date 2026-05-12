package com.app.tallerRetrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RecetasUiState {
    object Loading : RecetasUiState()
    data class Success(val receta: Receta) : RecetasUiState()
    data class Error(val message: String) : RecetasUiState()
}

class RecetasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RecetasUiState>(RecetasUiState.Loading)
    val uiState: StateFlow<RecetasUiState> = _uiState

    init {
        fetchRandomReceta()
    }

    fun fetchRandomReceta() {
        viewModelScope.launch {
            _uiState.value = RecetasUiState.Loading
            try {
                val response = RecetasInstance.api.getRandomReceta()
                val receta = response.meals.firstOrNull()
                if (receta != null) {
                    _uiState.value = RecetasUiState.Success(receta)
                } else {
                    _uiState.value = RecetasUiState.Error("No se encontró receta")
                }
            } catch (e: Exception) {
                _uiState.value = RecetasUiState.Error("Error: ${e.message}")
            }
        }
    }
}