package ru.tinkoff.myupgradeapplication.compose

import androidx.compose.ui.test.SemanticsMatcher

fun hasError(): SemanticsMatcher {
    return SemanticsMatcher.expectValue(IsErrorKey, true)
}

fun hasNoError(): SemanticsMatcher {
    return SemanticsMatcher.expectValue(IsErrorKey, false)
}
