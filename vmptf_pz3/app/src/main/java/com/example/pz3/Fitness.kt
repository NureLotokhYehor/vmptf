package com.example.pz3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Workout(val type: String, val durationMinutes: Int)

@Composable
fun Fitness() {
    var type by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var workouts by remember { mutableStateOf(listOf<Workout>()) }

    val totalDuration = workouts.sumOf { it.durationMinutes }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Фітнес-трекер", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Вид тренування (напр. Біг)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Тривалість (хвилини)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val dur = duration.toIntOrNull()
                if (type.isNotBlank() && dur != null) {
                    workouts = workouts + Workout(type, dur)
                    type = ""
                    duration = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Записати тренування")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Статистика: Всього хвилин: $totalDuration", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(workouts) { workout ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(workout.type, fontSize = 18.sp)
                        Text("${workout.durationMinutes} хв", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}