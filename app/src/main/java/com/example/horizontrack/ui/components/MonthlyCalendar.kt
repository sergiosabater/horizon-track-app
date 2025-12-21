package com.example.horizontrack.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Interactive monthly calendar component for habit tracking.
 * Shows the current month with completion status for each day.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyCalendar(
    completions: List<LocalDate>,
    onToggleDay: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    habitColor: Color = MaterialTheme.colorScheme.primary,
) {
    val today = LocalDate.now()
    val currentMonth = YearMonth.from(today)
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    
    // Calculate the first day to show (might be from previous month)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val startDate = firstDayOfMonth.minusDays((firstDayOfWeek.value % 7).toLong())
    
    val completionSet = completions.toSet()
    val weeks = mutableListOf<List<LocalDate>>()
    var currentDate = startDate
    
    // Build weeks
    while (currentDate <= lastDayOfMonth || currentDate.dayOfMonth <= 7) {
        val week = (0..6).map { currentDate.plusDays(it.toLong()) }
        weeks.add(week)
        currentDate = currentDate.plusDays(7)
        if (currentDate.isAfter(lastDayOfMonth) && currentDate.dayOfMonth > 7) break
    }
    
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // Month header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() } + " " + currentMonth.year,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Day names header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.name.take(1),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Calendar grid
        weeks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                week.forEach { date ->
                    val isCompleted = completionSet.contains(date)
                    val isToday = date == today
                    val isCurrentMonth = YearMonth.from(date) == currentMonth
                    
                    val bgColor = when {
                        isToday && isCompleted -> habitColor
                        isToday -> habitColor.copy(alpha = 0.3f)
                        isCompleted && isCurrentMonth -> habitColor.copy(alpha = 0.6f)
                        isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        else -> Color.Transparent
                    }
                    
                    val textColor = when {
                        isCompleted || (isToday && isCurrentMonth) -> MaterialTheme.colorScheme.onPrimary
                        isCurrentMonth -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    }
                    
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(bgColor)
                            .clickable(enabled = isCurrentMonth) { onToggleDay(date) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = textColor,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

