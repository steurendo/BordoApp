package com.steurendo.bordo.presentation.ui.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steurendo.bordo.data.prefs.AppPreferences
import com.steurendo.bordo.data.prefs.AppPreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroScreenViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            AppPreferences.getValueFlow(AppPreferencesKeys.showOnboarding, context)
                .collect { showOnboarding ->
                    _uiState.update {
                        it.copy(showOnboarding = showOnboarding)
                    }
                }
        }
    }
}