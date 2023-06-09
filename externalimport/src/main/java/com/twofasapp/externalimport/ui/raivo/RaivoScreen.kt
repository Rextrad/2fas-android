package com.twofasapp.externalimport.ui.raivo

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.twofasapp.core.encoding.encodeBase64ToString
import com.twofasapp.externalimport.ui.common.ImportDescription
import com.twofasapp.externalimport.ui.common.ImportFilePickerButton
import com.twofasapp.externalimport.ui.common.ImportFileScaffold
import com.twofasapp.navigation.ExternalImportDirections
import com.twofasapp.navigation.ExternalImportRouter
import com.twofasapp.resources.R
import org.koin.androidx.compose.get

@Composable
fun RaivoScreen(router: ExternalImportRouter = get()) {

    ImportFileScaffold(
        title = stringResource(id = R.string.externalimport_raivo),
        image = painterResource(id = R.drawable.ic_import_raivo),
        description = { ImportDescription(text = stringResource(id = R.string.externalimport__raivo_msg)) },
        actions = {
            ImportFilePickerButton(
                text = stringResource(id = R.string.externalimport__choose_json_cta),
                fileType = "application/json",
                onFilePicked = {
                    router.navigate(
                        ExternalImportDirections.ImportResult(
                            type = ExternalImportDirections.ImportResult.Type.Raivo,
                            content = it.toString().encodeBase64ToString()
                        )
                    )
                }
            )
        },
        onBackClick = { router.navigateBack() }
    )
}