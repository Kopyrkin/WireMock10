package ru.tinkoff.myupgradeapplication.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.compose.ui.theme.MyUpgradeApplicationTheme

@Composable
fun Greeting() {
    MyUpgradeApplicationTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag(GreetingTestTags.SCREEN),
            color = MaterialTheme.colorScheme.background
        ) {
            var name by remember { mutableStateOf("") }
            val buttonEnabled by remember {
                derivedStateOf { name.isNotBlank() }
            }
            var greetingText by remember { mutableStateOf<String?>(null) }
            var error by remember { mutableStateOf<ValidationError?>(null) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .semantics {
                                isError = error != null
                                testTag = GreetingTestTags.NAME_TEXT_INPUT
                            },
                        value = name,
                        label = {
                            Text(
                                modifier = Modifier.testTag(GreetingTestTags.NAME_TEXT_INPUT_LABEL),
                                text = stringResource(R.string.name_input_label)
                            )
                        },
                        onValueChange = {
                            name = it
                            greetingText = null
                            error = null
                        },
                        isError = error != null
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = {
                            when {
                                name.any { it.isDigit() } -> {
                                    error = ValidationError.Digits
                                }

                                name.length < 2 -> {
                                    error = ValidationError.Size
                                }

                                else -> {
                                    greetingText = name
                                }
                            }

                        },
                        enabled = buttonEnabled
                    ) {
                        Text(text = stringResource(R.string.ok_button))
                    }
                }
                greetingText?.let { greeting ->
                    Text(
                        modifier = Modifier.testTag(GreetingTestTags.GREETING_TEXT_TAG),
                        text = stringResource(R.string.greeting_message, greeting),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                error?.let { error ->
                    Text(
                        text = stringResource(error.errorRes),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }
}



sealed class ValidationError(@StringRes val errorRes: Int) {
    object Digits : ValidationError(R.string.error_digits)
    object Size : ValidationError(R.string.error_size)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}