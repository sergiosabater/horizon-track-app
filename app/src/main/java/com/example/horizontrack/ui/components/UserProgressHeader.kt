package com.example.horizontrack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.repository.UserProgressRepository

@Composable
fun UserProgressHeader(
    userProgressRepository: UserProgressRepository,
    modifier: Modifier = Modifier,
) {
    val progressFlow = userProgressRepository.observeProgress()
    val progress by progressFlow.collectAsState(
        initial = null,
    )

    val p = progress
    if (p != null) {
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
                progress = { p.currentXp.toFloat() / p.xpForNextLevel.toFloat() },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${p.currentXp} / ${p.xpForNextLevel} XP · Streak: ${p.currentStreakDays} days",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


