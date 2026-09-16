package com.steurendo.bordo.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.steurendo.bordo.presentation.navigation.destinations.DefineLayoutDestination
import com.steurendo.bordo.presentation.navigation.destinations.HomeDestination
import com.steurendo.bordo.presentation.navigation.destinations.IntroDestination
import com.steurendo.bordo.presentation.navigation.destinations.OnboardingDestination
import com.steurendo.bordo.presentation.navigation.destinations.PhotoCroppingDestination
import com.steurendo.bordo.presentation.navigation.destinations.PreviewAlbumDestination
import com.steurendo.bordo.presentation.navigation.destinations.SelectPaddingEffectDestination
import com.steurendo.bordo.presentation.ui.crop_photo.PhotoCroppingPage
import com.steurendo.bordo.presentation.ui.crop_photo.PhotoCroppingViewModel
import com.steurendo.bordo.presentation.ui.effectselection.SelectPaddingEffectPage
import com.steurendo.bordo.presentation.ui.effectselection.SelectPaddingEffectViewModel
import com.steurendo.bordo.presentation.ui.home.HomePage
import com.steurendo.bordo.presentation.ui.intro.IntroPage
import com.steurendo.bordo.presentation.ui.layoutdefinition.DefineLayoutPage
import com.steurendo.bordo.presentation.ui.layoutdefinition.DefineLayoutViewModel
import com.steurendo.bordo.presentation.ui.onboarding.OnboardingPage
import com.steurendo.bordo.presentation.ui.preview.PreviewAlbumPage
import com.steurendo.bordo.presentation.ui.preview.PreviewAlbumViewModel

@Composable
fun BordoNavigator() {
    val backStack = rememberNavBackStack(IntroDestination)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        entryProvider = { key ->
            when (key) {
                is IntroDestination -> NavEntry(key) {
                    IntroPage(
                        onSplashFinished = { showOnboarding ->
                            backStack.clear()
                            backStack.add(if (showOnboarding) OnboardingDestination else HomeDestination)
                        },
                        vm = hiltViewModel()
                    )
                }

                is OnboardingDestination -> NavEntry(key) {
                    OnboardingPage(onClickDone = {
                        backStack.removeLastOrNull()
                        backStack.add(HomeDestination)
                    })
                }

                is HomeDestination -> NavEntry(key) {
                    HomePage(
                        onClickNewAlbum = {
                            backStack.add(
                                DefineLayoutDestination(
                                    selectedPhotoIndex = 0,
                                    changeMode = false
                                )
                            )
                        },
                        onPreviewAlbum = {
                            backStack.add(
                                PreviewAlbumDestination(
                                    albumId = it.id,
                                    currentPhotoIndex = 0
                                )
                            )
                        },
                        vm = hiltViewModel()
                    )
                }

                is DefineLayoutDestination -> NavEntry(key) {
                    DefineLayoutPage(
                        onClickBack = { backStack.removeLastOrNull() },
                        onClickNext = {
                            backStack.add(
                                SelectPaddingEffectDestination(
                                    currentPhotoIndex = 0,
                                    changeMode = false
                                )
                            )
                        },
                        onClickSave = { albumId, selectedPhotoIndex ->
                            backStack.clear()
                            backStack.addAll(
                                listOf(
                                    HomeDestination, PreviewAlbumDestination(
                                        albumId = albumId,
                                        currentPhotoIndex = selectedPhotoIndex
                                    )
                                )
                            )
                        },
                        onClickCrop = { selectedPhotoIndex ->
                            backStack.add(
                                PhotoCroppingDestination(
                                    photoIndex = selectedPhotoIndex,
                                    changeMode = key.changeMode
                                )
                            )
                        },
                        vm = hiltViewModel<DefineLayoutViewModel, DefineLayoutViewModel.Factory>(
                            creationCallback = { factory ->
                                factory.create(key)
                            }
                        )
                    )
                }

                is PhotoCroppingDestination -> NavEntry(key) {
                    val destination = DefineLayoutDestination(
                        changeMode = key.changeMode,
                        selectedPhotoIndex = key.photoIndex
                    )
                    PhotoCroppingPage(
                        onClickBack = { backStack.removeLastOrNull() },
                        onClickSave = {
                            backStack.removeLastOrNull()
                            backStack.removeLastOrNull()
                            backStack.add(destination)
                        },
                        vm = hiltViewModel<PhotoCroppingViewModel, PhotoCroppingViewModel.Factory>(
                            creationCallback = { factory -> factory.create(key) })
                    )
                }

                is SelectPaddingEffectDestination -> NavEntry(key) {
                    SelectPaddingEffectPage(
                        onClickBack = { backStack.removeLastOrNull() },
                        onClickCreate = {
                            backStack.clear()
                            backStack.add(HomeDestination)
                        },
                        onClickSave = { albumId, currentPhotoIndex ->
                            val destination = PreviewAlbumDestination(
                                albumId = albumId,
                                currentPhotoIndex = currentPhotoIndex
                            )
                            backStack.removeLastOrNull()
                            backStack.removeLastOrNull()
                            backStack.add(destination)
                        },
                        vm = hiltViewModel<SelectPaddingEffectViewModel, SelectPaddingEffectViewModel.Factory>(
                            creationCallback = { factory -> factory.create(key) }),
                        renameVm = hiltViewModel()
                    )
                }

                is PreviewAlbumDestination -> NavEntry(key) {
                    PreviewAlbumPage(
                        onClickHome = {
                            backStack.clear()
                            backStack.add(HomeDestination)
                        },
                        onClickChangeReferencePhoto = { currentPhotoIndex ->
                            backStack.add(
                                DefineLayoutDestination(
                                    selectedPhotoIndex = currentPhotoIndex,
                                    changeMode = true
                                )
                            )
                        },
                        onClickChangeEffect = { currentPhotoIndex ->
                            backStack.add(
                                SelectPaddingEffectDestination(
                                    currentPhotoIndex = currentPhotoIndex,
                                    changeMode = true
                                )
                            )
                        },
                        vm = hiltViewModel<PreviewAlbumViewModel, PreviewAlbumViewModel.Factory>(
                            creationCallback = { factory -> factory.create(key) }),
                        renameVm = hiltViewModel()
                    )
                }

                else -> NavEntry(key) {
                    Text("Unknown route")
                }
            }
        })
}