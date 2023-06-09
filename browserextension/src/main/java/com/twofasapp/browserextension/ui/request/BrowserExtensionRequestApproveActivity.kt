package com.twofasapp.browserextension.ui.request

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.twofasapp.base.BaseComponentActivity
import com.twofasapp.browserextension.notification.BrowserExtensionRequestPayload
import com.twofasapp.browserextension.notification.BrowserExtensionRequestReceiver
import com.twofasapp.design.theme.AppThemeLegacy

class BrowserExtensionRequestApproveActivity : BaseComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val payload = intent.getParcelableExtra<BrowserExtensionRequestPayload>(BrowserExtensionRequestPayload.Key)!!

        setContent {
            AppThemeLegacy {
                Surface {
                    sendBroadcast(BrowserExtensionRequestReceiver.createIntent(this, payload))
                    finish()
                }
            }
        }
    }
}
