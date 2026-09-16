package com.steurendo.bordo.presentation.ui.effectselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.usecases.album_management.GetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.albums.PersistAlbumUseCase
import com.steurendo.bordo.domain.usecases.albums.UpsertAlbumUseCase
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.mapper.toDomain
import com.steurendo.bordo.presentation.mapper.toUiModel
import com.steurendo.bordo.presentation.navigation.destinations.SelectPaddingEffectDestination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SelectPaddingEffectViewModel.Factory::class)
class SelectPaddingEffectViewModel @AssistedInject constructor(
    getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val upsertAlbumUseCase: UpsertAlbumUseCase,
    private val persistAlbumUseCase: PersistAlbumUseCase,
    // Params
    @Assisted val navKey: SelectPaddingEffectDestination
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(navKey: SelectPaddingEffectDestination): SelectPaddingEffectViewModel
    }

    private val _uiState = MutableStateFlow(SelectPaddingEffectUiState())
    val uiState: StateFlow<SelectPaddingEffectUiState> = _uiState.asStateFlow()

    init {
        val album: AlbumUiModel =
            checkNotNull(getCurrentAlbumManagementUseCase.invoke().toUiModel())
        _uiState.update {
            it.copy(
                album = album,
                changeMode = navKey.changeMode,
                currentPhotoIndex = navKey.currentPhotoIndex
            )
        }
    }

    fun setPaddingEffect(effect: PaddingEffect) {
        if (effect != _uiState.value.album.paddingEffect)
            _uiState.update {
                it.copy(
                    album = it.album.copy(
                        paddingEffect = effect,
                        effectParameters = EffectParams.init(effect)
                    )
                )
            }
    }

    fun setEffectParameters(parameters: EffectParams) =
        _uiState.update { it.copy(album = it.album.copy(effectParameters = parameters)) }

    fun toggleVisibleSaveDialog(visible: Boolean) =
        _uiState.update { it.copy(visibleRenameDialogBox = visible) }

    fun setAlbumName(name: String) = _uiState.update { it.copy(album = it.album.copy(name = name)) }

    fun updateAlbum() {
        viewModelScope.launch {
            upsertAlbumUseCase.invoke(album = _uiState.value.album.toDomain())
        }
    }

    fun upsertNewAlbum() {
        viewModelScope.launch {
            persistAlbumUseCase.invoke(_uiState.value.album.toDomain())
        }
    }
}