package com.example.horizontrack.ui.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.repository.AchievementRepository
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.domain.repository.UserProgressRepository
import com.example.horizontrack.ui.components.MonthlyCalendar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Detail screen for a specific habit.
 * Shows basic info, streaks and a lightweight 7-day calendar strip.
 */
@Composable
fun HabitDetailScreen(
    habitId: Long,
    habitRepository: HabitRepository,
    userProgressRepository: UserProgressRepository,
    achievementRepository: AchievementRepository,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val habitProgressFlow = habitRepository.observeHabitProgress(habitId)
    val habitProgress by habitProgressFlow.collectAsState(initial = null)

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Habit Detail",
                    style = MaterialTheme.typography.headlineMedium,
                )
                IconButton(onClick = onNavigateToEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit habit",
                    )
                }
            }
        },
    ) { paddingValues ->
        val progress = habitProgress
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopStart,
        ) {
            if (progress == null) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp),
                )
            } else {
                val habitColor = Color(progress.habit.colorHex.toInt())
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        text = progress.habit.name,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    if (progress.habit.description.isNotBlank()) {
                        Text(
                            text = progress.habit.description,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                    }
                    Text(
                        text = "Current streak: ${progress.currentStreak} days",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Longest streak: ${progress.longestStreak} days",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))

                    // Monthly calendar
                    MonthlyCalendar(
                        completions = progress.completions,
                        onToggleDay = { date ->
                            scope.launch {
                                habitRepository.toggleCompletion(habitId, date)
                                if (date == LocalDate.now()) {
                                    userProgressRepository.addXp(10)
                                    userProgressRepository.registerDayWithCompletion()
                                    // Check for achievements
                                    val progress = userProgressRepository.observeProgress().first()
                                    achievementRepository.checkAndUnlockAchievements(progress)
                                }
                            }
                        },
                        habitColor = habitColor,
                    )

                    Spacer(modifier = Modifier.padding(top = 24.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                val today = LocalDate.now()
                                habitRepository.toggleCompletion(habitId, today)
                                userProgressRepository.addXp(10)
                                userProgressRepository.registerDayWithCompletion()
                                // Check for achievements
                                val progress = userProgressRepository.observeProgress().first()
                                achievementRepository.checkAndUnlockAchievements(progress)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "Mark today as done (+10 XP)")
                    }
                }
            }
        }
    }
}


