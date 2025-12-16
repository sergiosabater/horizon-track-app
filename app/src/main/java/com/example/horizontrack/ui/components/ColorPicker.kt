package com.example.horizontrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

/**
 * Simple color picker for habit customization.
 * Shows predefined colors in a row.
 */
@Composable
fun ColorPicker(
    selectedColorHex: Long,
    onColorSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        0xFF6650A4L to "Purple",
        0xFF2196F3L to "Blue",
        0xFF4CAF50L to "Green",
        0xFFFF9800L to "Orange",
        0xFFE91E63L to "Pink",
        0xFF00BCD4L to "Cyan",
        0xFF9C27B0L to "Deep Purple",
        0xFFF44336L to "Red",
    )
    
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Color",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            colors.forEach { (colorHex, _) ->
                val isSelected = selectedColorHex == colorHex
                val color = Color(colorHex.toInt())
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .then(
                            if (isSelected) {
                                Modifier.border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = CircleShape,
                                )
                            } else {
                                Modifier
                            }
                        )
                        .clickable { onColorSelected(colorHex) },
                )
            }
        }
    }
}

