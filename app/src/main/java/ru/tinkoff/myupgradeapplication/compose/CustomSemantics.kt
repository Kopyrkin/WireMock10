package ru.tinkoff.myupgradeapplication.compose

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val IsErrorKey = SemanticsPropertyKey<Boolean>("IsErrorKey")
var SemanticsPropertyReceiver.isError: Boolean by IsErrorKey