package com.twofasapp.services.ui.changebrand

import com.twofasapp.services.domain.model.BrandIcon

internal data class ChangeBrandUiState(
    val sections: Map<String, List<BrandIcon>> = emptyMap(),
    val scrollTo: Boolean = false,
)