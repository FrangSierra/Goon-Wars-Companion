package com.durdinstudios.goonwarscollector.ui.components.textfields

import com.durdinstudios.goonwarscollector.R

/**
 * Representation of a [TextFieldState] for wallets.
 */
class WalletAddressState :
    TextFieldState(validator = { username -> validateUsername(username) }, errorFor = ::errorForUsernameError)

private fun errorForUsernameError(error: TextFieldState.FieldError): Int? = when (error) {
    TextFieldState.FieldError.NO_ERROR -> null
    TextFieldState.FieldError.REQUIRED -> R.string.general_form_error_required_field
    TextFieldState.FieldError.WRONG_FORMAT -> R.string.general_form_error_invalid_format
}

private fun validateUsername(wallet: String): TextFieldState.FieldError =
    wallet.trim().let { cleanUsername ->
        when {
            cleanUsername.isBlank() -> TextFieldState.FieldError.REQUIRED
            else -> TextFieldState.FieldError.NO_ERROR
        }
    }