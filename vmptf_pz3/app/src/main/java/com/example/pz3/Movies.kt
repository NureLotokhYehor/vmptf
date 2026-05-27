package com.example.pz3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Movie(val name: String, val genre: String, val rating: Double)

@Composable
fun Movies() {
    val movieList = listOf(
        Movie("The Matrix", "Sci-Fi", 8.7),
        Movie("Inception", "Sci-Fi", 8.8),
        Movie("The Dark Knight", "Action", 9.0),
        Movie("Interstellar", "Sci-Fi", 8.6)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Список фільмів", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(movieList) { movie ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = movie.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Жанр: ${movie.genre}", fontSize = 16.sp)
                        Text(text = "Рейтинг: ${movie.rating}/10", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}