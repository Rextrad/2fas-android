package com.twofasapp.designsystem.service.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twofasapp.designsystem.TwTheme

@Composable
fun ServiceTimer(
    timer: Int,
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val progressFraction by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            progress = progressFraction,
            color = TwTheme.color.onSurfacePrimary,
            strokeWidth = 2.dp,
            modifier = Modifier.size(32.dp),
        )

        Text(
            text = timer.toString(),
            style = TwTheme.typo.caption,
            color = TwTheme.color.onSurfacePrimary
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ServiceTimer(timer = 10, progress = 0.33f)
}