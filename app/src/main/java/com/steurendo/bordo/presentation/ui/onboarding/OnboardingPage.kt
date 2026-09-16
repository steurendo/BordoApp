package com.steurendo.bordo.presentation.ui.onboarding

import android.content.Context
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.data.prefs.AppPreferences
import com.steurendo.bordo.data.prefs.AppPreferencesKeys
import kotlinx.coroutines.launch

@Composable
fun OnboardingPage(
    onClickDone: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 5 })

    OnboardingTemplate(
        pagerState = pagerState,
        onClickNext = {
            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
        },
        onFinish = {
            coroutineScope.launch { skip(context) }
            onClickDone()
        }
    )
}

suspend fun skip(context: Context) {
    AppPreferences.setValue(
        preference = AppPreferencesKeys.showOnboarding,
        value = false,
        context = context
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPagePreview() {
    OnboardingPage(onClickDone = {})
}