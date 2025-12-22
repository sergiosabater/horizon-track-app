package com.example.horizontrack.ui.habits

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horizontrack.domain.model.Achievement
import com.example.horizontrack.domain.model.HabitProgress
import com.example.horizontrack.domain.repository.AchievementRepository
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.domain.repository.UserProgressRepository
import java.time.LocalDate

/**
 * Main screen showing list of habits with a gamified header.
 */
@RequiresApi(Build.VERSION_CODES.O)
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CompactProgressHeader(
                userProgressRepository = userProgressRepository,
                onNavigateToStatistics = onNavigateToStatistics,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHabit,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add habit",
                )
            }
        },
    ) { paddingValues ->
        if (habits.isEmpty()) {
            EmptyHabitsState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            ) {
                // Logros destacados
                item {
                    CompactAchievementsList(
                        achievementRepository = achievementRepository,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                }

                // Lista de hábitos rediseñada
                items(habits) { habitProgress: HabitProgress ->
                    ModernHabitCard(
                        progress = habitProgress,
                        onClick = { onNavigateToHabitDetail(habitProgress.habit.id) },
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CompactProgressHeader(
    userProgressRepository: UserProgressRepository,
    onNavigateToStatistics: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progressFlow = userProgressRepository.observeProgress()
    val progress by progressFlow.collectAsState(initial = null)

    progress?.let { p ->
        val animatedProgress by animateFloatAsState(
            targetValue = p.currentXp.toFloat() / p.xpForNextLevel.toFloat(),
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            label = "xpProgress",
        )

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.tertiary,
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "${p.level}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Level ${p.level}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${p.currentXp} / ${p.xpForNextLevel} XP",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    // Streak y botón de stats
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Streak badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "🔥",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${p.currentStreakDays}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        }

                        // Botón de estadísticas
                        IconButton(onClick = onNavigateToStatistics) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Statistics",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Barra de progreso con gradiente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(animatedProgress)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun ModernHabitCard(
    progress: HabitProgress,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val habitColor = Color(progress.habit.colorHex.toInt())

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale",
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 1.dp,
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            habitColor.copy(alpha = 0.15f),
                            Color.Transparent,
                        ),
                        startX = 0f,
                        endX = 400f,
                    )
                ),
        ) {
            // Barra lateral con color del hábito
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(habitColor)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
            ) {
                // Nombre y descripción
                Text(
                    text = progress.habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (progress.habit.description.isNotBlank()) {
                    Text(
                        text = progress.habit.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats con badges visuales
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Current streak
                    StatBadge(
                        icon = "🔥",
                        value = "${progress.currentStreak}",
                        label = "current",
                        color = habitColor,
                    )

                    // Longest streak
                    StatBadge(
                        icon = "⭐",
                        value = "${progress.longestStreak}",
                        label = "best",
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    // Completions
                    StatBadge(
                        icon = "✓",
                        value = "${progress.completions.size}",
                        label = "total",
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
        }
    }
}


@Composable
private fun StatBadge(
    icon: String,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            )
        }
    }
}

@Composable
private fun EmptyHabitsState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Emoji grande
        Text(
            text = "🎯",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 72.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No habits yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tap the + button to create your first habit\nand start your journey!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CompactAchievementsList(
    achievementRepository: AchievementRepository,
    modifier: Modifier = Modifier,
) {
    val achievementsFlow = achievementRepository.observeAchievements()
    val achievements by achievementsFlow.collectAsState(initial = emptyList())
    val unlockedAchievements = achievements.filter { it.unlockedAt != null }

    if (unlockedAchievements.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Recent Achievements",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(unlockedAchievements.take(5)) { achievement ->
                    CompactAchievementBadge(achievement = achievement)
                }
            }
        }
    }
}

@Composable
private fun CompactAchievementBadge(
    achievement: Achievement,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        tonalElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "🏆",
                style = MaterialTheme.typography.bodyMedium,
            )
            Column {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
            }
        }
    }
}