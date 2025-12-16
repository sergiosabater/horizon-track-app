package com.example.horizontrack.ui.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.model.HabitProgress
import com.example.horizontrack.domain.repository.AchievementRepository
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.domain.repository.UserProgressRepository
import com.example.horizontrack.ui.components.AchievementsList
import com.example.horizontrack.ui.components.UserProgressHeader
import java.time.LocalDate

/**
 * Main screen showing list of habits with a gamified header.
 */
@Composable
fun HabitsListScreen(
    habitRepository: HabitRepository,
    onNavigateToHabitDetail: (Long) -> Unit,
    userProgressRepository: UserProgressRepository,
    achievementRepository: AchievementRepository,
    onAddHabit: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val habitsFlow = habitRepository.observeHabitsWithProgress(LocalDate.now())
    val habits by habitsFlow.collectAsState(initial = emptyList())

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "My Habits",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    IconButton(onClick = onNavigateToStatistics) {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "Statistics",
                        )
                    }
                }
                UserProgressHeader(
                    userProgressRepository = userProgressRepository,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddHabit) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add habit",
                )
            }
        },
    ) { paddingValues ->
        if (habits.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No habits yet.\nTap + to create one.",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                item {
                    AchievementsList(
                        achievementRepository = achievementRepository,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                }
                items(habits) { habitProgress: HabitProgress ->
                    HabitListItem(
                        progress = habitProgress,
                        onClick = { onNavigateToHabitDetail(habitProgress.habit.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun HabitListItem(
    progress: HabitProgress,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val habitColor = Color(progress.habit.colorHex.toInt())
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Color indicator bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxWidth()
                    .background(habitColor),
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = progress.habit.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (progress.habit.description.isNotBlank()) {
                Text(
                    text = progress.habit.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Streak: ${progress.currentStreak} days",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "  •  Longest: ${progress.longestStreak}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}}

