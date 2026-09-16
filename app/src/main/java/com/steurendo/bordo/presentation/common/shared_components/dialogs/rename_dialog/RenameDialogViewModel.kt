package com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumManagementRepository
import com.steurendo.bordo.domain.usecases.albums.GetAllAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RenameDialogViewModel @Inject constructor(
    private val albumManagementRepository: AlbumManagementRepository,
    private val getAllAlbumsUseCase: GetAllAlbumsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RenameDialogUiState())
    val uiState: StateFlow<RenameDialogUiState> = _uiState.asStateFlow()

    private var usedNames: List<String> = emptyList()
    private var nextName: String = ""

    init {
        val isNewAlbum = albumManagementRepository.getAlbum().name == ""
        viewModelScope.launch {
            usedNames = getAllAlbumsUseCase.invoke().map { it.map(Album::name) }.first()
            nextName = computeNextName()
            _uiState.update {
                val defaultName =
                    if (isNewAlbum) nextName else albumManagementRepository.getAlbum().name
                return@update it.copy(
                    defaultName = defaultName,
                    isNameAvailable = !usedNames.contains(it.name) || it.name == ""
                )
            }
        }
    }

    fun setDefaultName(defaultName: String) = _uiState.update { it.copy(defaultName = defaultName) }

    fun setName(name: String) = _uiState.update {
        it.copy(
            name = name,
            isNameAvailable = !usedNames.contains(name) || name == ""
        )
    }

    fun resetName() = _uiState.update {
        it.copy(
            name = "",
            isNameAvailable = true
        )
    }

    fun computeNextName(): String {
        var nextAvailableName: String
        var firstAvailableNumber = 0
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = LocalDate.now().format(formatter)
        nextAvailableName = getFormattedName(dateString, firstAvailableNumber)
        while (usedNames.contains(nextAvailableName)) {
            firstAvailableNumber++
            nextAvailableName = getFormattedName(dateString, firstAvailableNumber)
        }
        return nextAvailableName
    }

    private fun getFormattedName(date: String, firstAvailableNumber: Int): String {
        if (firstAvailableNumber == 0)
            return "Album $date"
        return "Album $date ${firstAvailableNumber + 1}"
    }
}