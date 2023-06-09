package com.twofasapp.browserextension.ui.pairing.progress

import androidx.lifecycle.viewModelScope
import com.twofasapp.base.BaseViewModel
import com.twofasapp.base.dispatcher.Dispatchers
import com.twofasapp.browserextension.domain.PairBrowserCase
import com.twofasapp.browserextension.domain.RegisterMobileDeviceCase
import com.twofasapp.network.exception.BrowserAlreadyPairedException
import com.twofasapp.permissions.NotificationsPermissionRequestFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PairingProgressViewModel(
    private val dispatchers: Dispatchers,
    private val registerMobileDeviceCase: RegisterMobileDeviceCase,
    private val pairBrowserCase: PairBrowserCase,
    private val notificationsPermissionRequestFlow: NotificationsPermissionRequestFlow,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PairingProgressUiState())
    val uiState = _uiState.asStateFlow()

    fun pairBrowser(extensionId: String) {
        viewModelScope.launch(dispatchers.io()) {
            registerMobileDeviceCase()

            try {
                delay(500)
                pairBrowserCase(extensionId = extensionId)
                _uiState.update {
                    it.copy(
                        isPairing = false,
                        isPairingSuccess = true,
                        code = null,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()

                if (e is BrowserAlreadyPairedException) {
                    _uiState.update {
                        it.copy(
                            isPairing = false,
                            isPairingSuccess = true,
                            code = 409,
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isPairing = false,
                            isPairingSuccess = false,
                            code = null,
                        )
                    }
                }
            }
        }
    }
}