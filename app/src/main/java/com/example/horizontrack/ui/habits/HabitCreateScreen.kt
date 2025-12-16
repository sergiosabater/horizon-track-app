package com.example.horizontrack.ui.habits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.horizontrack.domain.model.Habit
import com.example.horizontrack.domain.repository.HabitRepository
import com.example.horizontrack.ui.components.ColorPicker
import com.example.horizontrack.ui.components.DayOfWeekSelector
import kotlinx.coroutines.launch
import java.time.DayOfWeek

/**
 * Simple form to create a new habit.
 * Later we can expand this with days of week, color picker, goals, etc.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCreateScreen(
    habitRepository: HabitRepository,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var selectedDays by remember { mutableStateOf(DayOfWeek.entries.toSet()) }
    var selectedColor by remember { mutableStateOf(0xFF6650A4L) }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "New Habit") },
            )
        },
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Active Days",
                style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DayOfWeekSelector(
                selectedDays = selectedDays,
                onDayToggle = { day ->
                    selectedDays = if (selectedDays.contains(day)) {
                        selectedDays - day
                    } else {
                        selectedDays + day
                    }
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            ColorPicker(
                selectedColorHex = selectedColor,
                onColorSelected = { selectedColor = it },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.text.isNotBlank()) {
                        scope.launch {
                            habitRepository.createHabit(
                                Habit(
                                    name = name.text.trim(),
                                    description = description.text.trim(),
                                    targetPerWeek = selectedDays.size,
                                    activeDays = selectedDays,
                                    colorHex = selectedColor,
                                ),
                            )
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 24.dp),
            ) {
                Text(text = "Create habit")
            }
        }
    }
}


