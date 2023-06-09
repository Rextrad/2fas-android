package com.twofasapp.externalimport.ui.googleauthenticator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twofasapp.design.compose.ButtonShape
import com.twofasapp.design.compose.ButtonTextColor
import com.twofasapp.design.compose.Toolbar
import com.twofasapp.design.compose.dialogs.RationaleDialog
import com.twofasapp.navigation.ExternalImportDirections
import com.twofasapp.navigation.ExternalImportRouter
import com.twofasapp.permissions.CameraPermissionRequestFlow
import com.twofasapp.permissions.PermissionStatus
import com.twofasapp.resources.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import org.koin.androidx.compose.get

@Composable
fun GoogleAuthenticatorScreen(
    router: ExternalImportRouter = get(),
    cameraPermissionRequest: CameraPermissionRequestFlow = get(),
) {
    var showRationaleDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { Toolbar(title = stringResource(id = R.string.externalimport_google_authenticator)) { router.navigateBack() } }
    ) { padding ->

        Column(
            Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.import_google_authenticator),
                    contentDescription = null,
                    modifier = Modifier.height(100.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.introduction__google_authenticator_import_process),
                    style = MaterialTheme.typography.body1.copy(lineHeight = 22.sp, fontSize = 17.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            Button(
                onClick = {
                    cameraPermissionRequest.execute()
                        .take(1)
                        .onEach {
                            when (it) {
                                PermissionStatus.GRANTED -> router.navigate(ExternalImportDirections.ImportScan())
                                PermissionStatus.DENIED -> Unit
                                PermissionStatus.DENIED_NEVER_ASK -> showRationaleDialog = true
                            }
                        }.launchIn(coroutineScope)
                },
                shape = ButtonShape(),
                modifier = Modifier
                    .height(48.dp)
                    .align(CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.commons__scan_qr_code).uppercase(), color = ButtonTextColor())
            }

            TextButton(
                onClick = {
                    cameraPermissionRequest.execute()
                        .take(1)
                        .onEach {
                            when (it) {
                                PermissionStatus.GRANTED -> router.navigate(ExternalImportDirections.ImportScan(startWithGallery = true))
                                PermissionStatus.DENIED -> Unit
                                PermissionStatus.DENIED_NEVER_ASK -> showRationaleDialog = true
                            }
                        }.launchIn(coroutineScope)
                },
                shape = ButtonShape(),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 8.dp)
                    .height(48.dp)
                    .align(CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.introduction__choose_qr_code).uppercase())
            }
        }

        if (showRationaleDialog) {
            RationaleDialog(
                title = stringResource(id = R.string.permissions__camera_permission),
                text = stringResource(id = R.string.permissions__camera_permission_description),
                onDismiss = { showRationaleDialog = false }
            )
        }
    }
}