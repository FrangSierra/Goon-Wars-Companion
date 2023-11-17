package com.durdinstudios.goonwarscollector.ui.components.textfields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.AppIcon
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.Heading2
import com.durdinstudios.goonwarscollector.ui.components.modifiers.autofill
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.textFieldAssistiveTextActiveTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.textFieldAssistiveTextErrorTextStyle

@Composable
fun WalletTextField(
    modifier: Modifier = Modifier,
    label: String = "Username",
    inputTestTag: String? = null,
    walletState: TextFieldState = remember { WalletAddressState() },
    showLeadingIcon: Boolean = true,
    textStyle: TextStyle = TextStyles.textFieldInputTextTextStyle.textStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    GenericOutlinedTextField(
        modifier = modifier,
        label = label,
        inputTestTag = inputTestTag,
        validationState = walletState,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = textStyle,
        leadingIcon = if (!showLeadingIcon) {
            null
        } else {
            { AppIcon(resId = R.drawable.ic_wallet, contentDescription = null, tint = LocalContentColor.current) }
        },
        trailingIcon = if (walletState.showErrors()) {
            {
                AppIcon(
                    resId = R.drawable.ic_text_field_error_trailing_icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = LocalContentColor.current
                )
            }
        } else {
            null
        },
        autofillTypes = listOf(AutofillType.Username),
    )
}

@Composable
fun GenericOutlinedTextField(
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    label: String,
    helperText: String? = null,
    inputTestTag: String? = null,
    validationState: TextFieldState = remember { WalletAddressState() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    autofillTypes: List<AutofillType> = emptyList(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyles.textFieldInputTextTextStyle.textStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onBeforeValueChange: (String) -> String = { it }
) {
    CoreOutlinedTextField(
        maxLength = maxLength,
        value = validationState.text,
        inputTestTag = inputTestTag,
        onBeforeValueChange = onBeforeValueChange,
        onValueChange = {
            validationState.text = it
        },
        label = label,
        modifier = modifier
            .autofill(
                autofillTypes = autofillTypes,
                onFill = { validationState.text = it }
            )
            .onFocusChanged { focusState ->
                val focused = focusState.isFocused
                validationState.onFocusChange(focused)
                if (!focused) {
                    validationState.enableShowErrors()
                }
            },
        helperText = helperText,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        textStyle = textStyle,
        isError = validationState.showErrors(),
        errorText = validationState.getErrorTextId()?.let { stringResource(id = it) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun CoreOutlinedTextField(
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    value: String = "",
    inputTestTag: String? = null,
    onBeforeValueChange: (String) -> String = { it },
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyles.textFieldInputTextTextStyle.textStyle,
    label: String = "Label",
    placeholder: String = "Placeholder",
    helperText: String? = null,
    errorText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.secondary,
        focusedLabelColor = MaterialTheme.colors.secondary,
        cursorColor = MaterialTheme.colors.secondary,
        errorLeadingIconColor = MaterialTheme.colors.error,
        errorTrailingIconColor = MaterialTheme.colors.error
    )
) {
    Column(modifier) {
        OutlinedTextField(
            value,
            onValueChange = {
                // First process text if needed
                var input = onBeforeValueChange(it)

                // Then limit the max length of the string, then pass it
                input = input.take(maxLength)
                onValueChange(input)
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(if (!inputTestTag.isNullOrBlank()) Modifier.testTag(inputTestTag) else Modifier),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            placeholder = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = placeholder)
                }
            },
            label = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = label)
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )

        if (isError) {
            errorText?.let { TextFieldRow(Modifier.testTag(TEXT_FIELD_ERROR_TEXT_DEFAULT_TAG), it, true) }
        } else {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                helperText?.let { TextFieldRow(Modifier.testTag(TEXT_FIELD_HELPER_TEXT_DEFAULT_TAG), it, false) }
            }
        }
    }
}

@Composable
fun SkeletonTextField(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = Color.Gray
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .withPlaceholder()
                        .wrapContentHeight(CenterVertically)
                        .size(height = 16.dp, width = 240.dp)
                        .clip(RectangleShape)
                        .background(Color.Gray)
                )
            }
        }
    }
}

const val TEXT_FIELD_HELPER_TEXT_DEFAULT_TAG = "textFieldHelperText"
const val TEXT_FIELD_ERROR_TEXT_DEFAULT_TAG = "textFieldErrorText"

@Composable
private fun TextFieldRow(modifier: Modifier = Modifier, text: String, isError: Boolean) {
    Text(
        text = text,
        modifier = modifier.padding(start = 16.dp, top = 4.dp),
        style = (if (isError) textFieldAssistiveTextErrorTextStyle else textFieldAssistiveTextActiveTextStyle).textStyle
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF024728)
@Composable
private fun OutlineTextFieldsPreview() {
    AppPreview(modifier = Modifier.padding(16.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Heading2(text = "Text field / Empty")
            CoreOutlinedTextField(helperText = "Helper text")
            Heading2(text = "Text field / Empty no helper")
            CoreOutlinedTextField()
            Heading2(text = "Wallet Text field / Empty")
            WalletTextField()
            Heading2(text = "Skeleton Text field")
            SkeletonTextField()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF024728)
@Composable
private fun OutlineTextFieldsErrorPreview() {
    AppPreview(modifier = Modifier.padding(16.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Heading2(text = "Text field / Empty error")
            CoreOutlinedTextField(isError = true, errorText = "Error Text")
            Heading2(text = "wallet Text field / Error")
            WalletTextField(walletState = WalletAddressState().apply {
                this.text = "tree"; this.forceShowErrors()
            })
        }
    }
}
