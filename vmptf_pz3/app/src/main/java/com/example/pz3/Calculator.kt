package com.example.pz3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculator() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Результат: ") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Простий калькулятор", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Перше число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Друге число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { result = calculateResult(num1, num2, "+") }) { Text("+") }
            Button(onClick = { result = calculateResult(num1, num2, "-") }) { Text("-") }
            Button(onClick = { result = calculateResult(num1, num2, "*") }) { Text("*") }
            Button(onClick = { result = calculateResult(num1, num2, "/") }) { Text("/") }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(result, fontSize = 20.sp)
    }
}

fun calculateResult(n1: String, n2: String, op: String): String {
    val a = n1.toDoubleOrNull()
    val b = n2.toDoubleOrNull()
    if (a == null || b == null) return "Помилка вводу"

    return "Результат: " + when(op) {
        "+" -> (a + b).toString()
        "-" -> (a - b).toString()
        "*" -> (a * b).toString()
        "/" -> if (b != 0.0) (a / b).toString() else "Ділення на нуль!"
        else -> ""
    }
}