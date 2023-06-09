package com.twofasapp.externalimport.ui.result

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.twofasapp.design.compose.ButtonShape
import com.twofasapp.design.compose.ButtonTextColor
import com.twofasapp.design.compose.Toolbar
import com.twofasapp.externalimport.domain.ExternalImport
import com.twofasapp.navigation.ExternalImportDirections.ImportResult.Type
import com.twofasapp.navigation.ExternalImportRouter
import com.twofasapp.resources.R
import org.koin.androidx.compose.get

@Composable
internal fun ImportResultScreen(
    type: String,
    content: String,
    viewModel: ImportResultViewModel = get(),
    router: ExternalImportRouter = get()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val activity = (LocalContext.current as? Activity)
    val importType = Type.valueOf(type)

    LaunchedEffect(Unit) {
        viewModel.import(importType, content)
    }

    if (uiState.finishSuccess) {
        Toast.makeText(activity, activity?.getString(R.string.backup__import_completed_successfuly), Toast.LENGTH_LONG).show()
        activity?.finish()
    }

    Scaffold(
        topBar = { Toolbar(title = stringResource(id = R.string.settings__external_import)) { router.navigateBack() } }
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
                    painter = painterResource(
                        id = when (importType) {
                            Type.GoogleAuthenticator -> R.drawable.ic_import_ga
                            Type.Aegis -> R.drawable.ic_import_aegis
                            Type.Raivo -> R.drawable.ic_import_raivo
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.height(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (uiState.title != null) {
                    Text(
                        text = stringResource(id = uiState.title),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.description != null) {
                    Text(
                        text = stringResource(id = uiState.description),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                if (uiState.servicesToImport != 0 && uiState.totalServicesCount != 0) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (uiState.servicesToImport == uiState.totalServicesCount) {
                            uiState.servicesToImport.toString()
                        } else {
                            stringResource(id = R.string.tokens__google_auth_out_of_title).format(uiState.servicesToImport, uiState.totalServicesCount)
                        },
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                if (uiState.footer != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = uiState.footer),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (uiState.importResult is ExternalImport.Success) {
                        viewModel.saveServices()
                    } else {
                        router.navigateBack()
                    }
                },
                shape = ButtonShape(),
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (uiState.button != null) {
                    Text(text = stringResource(id = uiState.button).uppercase(), color = ButtonTextColor())
                }
            }
        }
    }
}
