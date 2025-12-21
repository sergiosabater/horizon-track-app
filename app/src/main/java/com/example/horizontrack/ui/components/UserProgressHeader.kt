package com.example.horizontrack.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.repository.UserProgressRepository

@Composable
fun UserProgressHeader(
    userProgressRepository: UserProgressRepository,
    modifier: Modifier = Modifier,
) {
    val progressFlow = userProgressRepository.observeProgress()
    val progress by progressFlow.collectAsState(initial = null)

    val p = progress
    if (p != null) {
        val animatedProgress by animateFloatAsState(
            targetValue = p.currentXp.toFloat() / p.xpForNextLevel.toFloat(),
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            label = "xpProgress",
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Level ${p.level}",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                strokeCap = StrokeCap.Round,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${p.currentXp} / ${p.xpForNextLevel} XP · Streak: ${p.currentStreakDays} days",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


