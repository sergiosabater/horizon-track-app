package com.example.horizontrack.ui.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.repository.AchievementRepository
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.domain.repository.UserProgressRepository
import java.time.LocalDate

/**
 * Statistics screen showing overall progress, achievements, and insights.
 */
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreen(
    habitRepository: HabitRepository,
    userProgressRepository: UserProgressRepository,
    achievementRepository: AchievementRepository,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val habitsFlow = habitRepository.observeHabitsWithProgress(LocalDate.now())
    val habits by habitsFlow.collectAsState(initial = emptyList())
    val progressFlow = userProgressRepository.observeProgress()
    val progress by progressFlow.collectAsState(initial = null)
    val achievementsFlow = achievementRepository.observeAchievements()
    val achievements by achievementsFlow.collectAsState(initial = emptyList())

    val unlockedAchievements = achievements.filter { it.unlockedAt != null }
    val totalCompletions = habits.sumOf { it.completions.size }
    val activeHabits = habits.size
    val totalStreakDays = habits.sumOf { it.currentStreak }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Statistics",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            // Overall Progress Card
            progress?.let { p ->
                StatCard(
                    title = "Overall Progress",
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column {
                        StatRow("Level", "${p.level}")
                        StatRow("Total XP", "${p.totalXp}")
                        StatRow("Current Streak", "${p.currentStreakDays} days")
                        StatRow("Longest Streak", "${p.longestStreakDays} days")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Habits Overview Card
            StatCard(
                title = "Habits Overview",
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    StatRow("Active Habits", "$activeHabits")
                    StatRow("Total Completions", "$totalCompletions")
                    StatRow("Total Streak Days", "$totalStreakDays")
                    if (activeHabits > 0) {
                        val avgStreak = totalStreakDays / activeHabits
                        StatRow("Average Streak", "$avgStreak days")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Achievements Card
            StatCard(
                title = "Achievements",
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    StatRow(
                        "Unlocked",
                        "${unlockedAchievements.size} / ${achievements.size}",
                    )
                    if (unlockedAchievements.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        unlockedAchievements.take(5).forEach { achievement ->
                            Text(
                                text = "⭐ ${achievement.title}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Habits Breakdown
            if (habits.isNotEmpty()) {
                StatCard(
                    title = "Habits Breakdown",
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column {
                        habits.forEach { habitProgress ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = habitProgress.habit.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = "${habitProgress.completions.size} completions",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            content()
        }
    }
}

@Composable
private fun StatRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        )
    }
}

