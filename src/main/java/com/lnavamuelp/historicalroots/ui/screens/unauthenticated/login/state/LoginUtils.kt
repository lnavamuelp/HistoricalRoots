package com.lnavamuelp.historicalroots.ui.screens.unauthenticated.login.state

import com.lnavamuelp.historicalroots.R
import com.lnavamuelp.historicalroots.ui.common.state.ErrorState

val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)