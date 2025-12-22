package com.example.horizontrack.ui.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.model.Achievement
import com.example.horizontrack.domain.model.HabitProgress
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
        containerColor = MaterialTheme.colorScheme.background,
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
                            fontWeight = FontWeight.Bold,
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            progress?.let { p ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Level Card
                    GradientStatCard(
                        modifier = Modifier.weight(1f),
                        icon = "🎯",
                        title = "Level",
                        value = "${p.level}",
                        subtitle = "${p.totalXp} total XP",
                        gradient = listOf(
                            Color(0xFF6366F1), // Indigo
                            Color(0xFF8B5CF6), // Purple
                        ),
                    )

                    // Current Streak Card
                    GradientStatCard(
                        modifier = Modifier.weight(1f),
                        icon = "🔥",
                        title = "Current Streak",
                        value = "${p.currentStreakDays}",
                        subtitle = "days in a row",
                        gradient = listOf(
                            Color(0xFFEF4444), // Red
                            Color(0xFFF97316), // Orange
                        ),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // XP Progress Card
                    GradientStatCard(
                        modifier = Modifier.weight(1f),
                        icon = "⭐",
                        title = "Next Level",
                        value = "${p.currentXp}",
                        subtitle = "/ ${p.xpForNextLevel} XP",
                        gradient = listOf(
                            Color(0xFFFBBF24), // Amber
                            Color(0xFFF59E0B), // Yellow
                        ),
                    )

                    // Longest Streak Card
                    GradientStatCard(
                        modifier = Modifier.weight(1f),
                        icon = "🏆",
                        title = "Best Streak",
                        value = "${p.longestStreakDays}",
                        subtitle = "days record",
                        gradient = listOf(
                            Color(0xFF10B981), // Green
                            Color(0xFF059669), // Emerald
                        ),
                    )
                }
            }

            // Habits Overview - Card grande con estadísticas
            HabitsOverviewCard(
                activeHabits = activeHabits,
                totalCompletions = totalCompletions,
                totalStreakDays = totalStreakDays,
            )

            // Achievements - Card colorida
            AchievementsCard(
                unlockedCount = unlockedAchievements.size,
                totalCount = achievements.size,
                recentAchievements = unlockedAchievements.take(3),
            )

            // Habits Breakdown - Lista detallada
            if (habits.isNotEmpty()) {
                HabitsBreakdownCard(habits = habits)
            }
        }
    }
}

@Composable
private fun GradientStatCard(
    icon: String,
    title: String,
    value: String,
    subtitle: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
) {
    // Animación del valor
    val animatedValue by remember(value) {
        mutableStateOf(value)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = gradient + listOf(gradient.last().copy(alpha = 0.8f)),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                    )
                )
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                // Icon y título
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = icon,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                }

                // Valor principal
                Column {
                    Text(
                        text = animatedValue,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.9f),
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f),
                    )
                }
            }
        }
    }
}

@Composable
private fun HabitsOverviewCard(
    activeHabits: Int,
    totalCompletions: Int,
    totalStreakDays: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Text(
                    text = "📊",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 12.dp),
                )
                Text(
                    text = "Habits Overview",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Stats en grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                MiniStatItem(
                    icon = "✓",
                    value = "$activeHabits",
                    label = "Active",
                    color = Color(0xFF3B82F6), // Blue
                )

                MiniStatItem(
                    icon = "🎯",
                    value = "$totalCompletions",
                    label = "Completions",
                    color = Color(0xFF8B5CF6), // Purple
                )

                MiniStatItem(
                    icon = "📈",
                    value = "$totalStreakDays",
                    label = "Total Days",
                    color = Color(0xFF10B981), // Green
                )
            }

            if (activeHabits > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Average Streak",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "${totalStreakDays / activeHabits} days",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}


@Composable
private fun MiniStatItem(
    icon: String,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.15f),
            modifier = Modifier.size(56.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color,
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ✅ NUEVO: Card de logros con progreso visual
@Composable
private fun AchievementsCard(
    unlockedCount: Int,
    totalCount: Int,
    recentAchievements: List<Achievement>,
    modifier: Modifier = Modifier,
) {
    val progress = if (totalCount > 0) unlockedCount.toFloat() / totalCount.toFloat() else 0f

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEC4899).copy(alpha = 0.15f), // Pink
                            Color(0xFFA855F7).copy(alpha = 0.15f), // Purple
                        ),
                    )
                )
                .padding(20.dp),
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    Text(
                        text = "🏆",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(end = 12.dp),
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Achievements",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "$unlockedCount / $totalCount unlocked",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    // Circular progress
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(60.dp),
                    ) {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxSize(),
                            color = Color(0xFFEC4899),
                            strokeWidth = 6.dp,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                if (recentAchievements.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    recentAchievements.forEach { achievement ->
                        AchievementBadge(achievement = achievement)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

// ✅ NUEVO: Badge de logro compacto
@Composable
private fun AchievementBadge(
    achievement: Achievement,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "⭐",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 12.dp),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun HabitsBreakdownCard(
    habits: List<HabitProgress>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Text(
                    text = "📋",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 12.dp),
                )
                Text(
                    text = "Habits Breakdown",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            habits.forEach { habitProgress ->
                val habitColor = Color(habitProgress.habit.colorHex.toInt())

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(habitColor),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = habitProgress.habit.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                            )
                        }

                        Text(
                            text = "${habitProgress.completions.size} completions",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = habitColor,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Barra de progreso
                    val maxCompletions = habits.maxOfOrNull { it.completions.size } ?: 1
                    val progress =
                        habitProgress.completions.size.toFloat() / maxCompletions.toFloat()

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = habitColor,
                        trackColor = habitColor.copy(alpha = 0.2f),
                    )
                }

                if (habitProgress != habits.last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                    )
                }
            }
        }
    }
}

