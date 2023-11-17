package com.durdinstudios.goonwarscollector.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Base implementation of a custom [TextField] state that needs a validator on their input.
 * This class must be extended by each specific case needed.
 *
 * @sample [WalletAddressState] [PasswordState]
 */
open class TextFieldState(
    private val validator: (String) -> FieldError = { FieldError.NO_ERROR },
    private val errorFor: (FieldError) -> Int? = { null }
) {
    @Suppress("UndocumentedPublicClass")
    enum class FieldError {
        NO_ERROR, REQUIRED, WRONG_FORMAT
    }

    var text: String by mutableStateOf("")

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text) == FieldError.NO_ERROR

    /**
     * Reports a change of focus.
     */
    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    /**
     * Enables displaying errors in the state.
     */
    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    /**
     * Force shows errors, useful when you need to display the error of the text field out of the usual flows.
     */
    fun forceShowErrors() {
        isFocusedDirty = true
        enableShowErrors()
    }

    /**
     * Returns if errors should be shown.
     */
    fun showErrors() = !isValid && displayErrors

    /**
     * Returns the error text resource ID.
     */
    @StringRes
    open fun getErrorTextId(): Int? {
        return if (showErrors()) {
            errorFor(validator(text))
        } else {
            null
        }
    }
}
