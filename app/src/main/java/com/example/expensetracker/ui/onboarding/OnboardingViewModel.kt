package com.example.expensetracker.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.common.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val name: String = "",
    val monthlySalary: String = "",
    val isNameError: Boolean = false,
    val isSalaryError: Boolean = false,
    val isCompleted: Boolean = false
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(
            name = newName,
            isNameError = false
        )
    }

    fun onSalaryChanged(newSalary: String) {
        if (newSalary.isEmpty() || newSalary.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.value = _uiState.value.copy(
                monthlySalary = newSalary,
                isSalaryError = false
            )
        }
    }

    fun completeOnboarding() {
        val currentName = _uiState.value.name.trim()
        val salaryDouble = _uiState.value.monthlySalary.toDoubleOrNull()

        val isNameInvalid = currentName.isEmpty()
        val isSalaryInvalid = salaryDouble == null || salaryDouble <= 0

        if (isNameInvalid || isSalaryInvalid) {
            _uiState.value = _uiState.value.copy(
                isNameError = isNameInvalid,
                isSalaryError = isSalaryInvalid
            )
            return
        }

        viewModelScope.launch {
            userPreferencesRepository.saveUserData(
                name = currentName,
                monthlySalary = salaryDouble!!
            )
            userPreferencesRepository.setOnboardingCompleted(true)
            _uiState.value = _uiState.value.copy(isCompleted = true)
        }
    }
}