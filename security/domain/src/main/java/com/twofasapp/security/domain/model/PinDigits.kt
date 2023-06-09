package com.twofasapp.security.domain.model

enum class PinDigits(
    val value: Int,
    val label: Int,
) {
    Code4(4, com.twofasapp.resources.R.string.settings__pin_4_digits),
    Code6(6, com.twofasapp.resources.R.string.settings__pin_6_digits),
}