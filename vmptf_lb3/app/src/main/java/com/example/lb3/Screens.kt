package com.example.lb3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CatalogScreen(viewModel: AppViewModel) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        if (viewModel.recommendations.isNotEmpty()) {
            item {
                Text("✨ Рекомендовано для вас", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(viewModel.recommendations) { product ->
                ProductCard(product, viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Всі товари", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            item { Text("Всі товари", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
        }

        items(viewModel.products) { product ->
            ProductCard(product, viewModel)
        }
    }
}

@Composable
fun ProductCard(product: Product, viewModel: AppViewModel) {
    val isWished = viewModel.wishlist.contains(product)
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { viewModel.toggleWishlist(product) }) {
                    Icon(
                        imageVector = if (isWished) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Бажане",
                        tint = if (isWished) Color.Red else Color.Gray
                    )
                }
            }
            Text("Категорія: ${product.category}", color = Color.Gray)
            Text("Ціна: $${product.price}", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.cart.add(product) }, modifier = Modifier.fillMaxWidth()) {
                Text("Додати в кошик")
            }
        }
    }
}

@Composable
fun WishlistScreen(viewModel: AppViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Список бажаного", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        if (viewModel.wishlist.isEmpty()) Text("Тут поки порожньо")
        LazyColumn {
            items(viewModel.wishlist) { product ->
                ProductCard(product, viewModel)
            }
        }
    }
}

@Composable
fun CartScreen(viewModel: AppViewModel) {
    val total = viewModel.cart.sumOf { it.price }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Кошик", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        if (viewModel.cart.isEmpty()) Text("Кошик порожній")

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.cart) { product ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(product.name)
                        Row {
                            Text("$${product.price}")
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.cart.remove(product) }) { Text("X") }
                        }
                    }
                }
            }
        }

        Text("До сплати: $$total", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.checkout() },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.cart.isNotEmpty()
        ) {
            Text("Оформити замовлення")
        }
        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(viewModel.errorMessage.value, color = Color.Red)
        }
    }
}

@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        if (viewModel.token.value == null) {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Text("Вхід / Реєстрація", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Логін") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Пароль") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.login(username, password) }, modifier = Modifier.fillMaxWidth()) {
                Text(if (viewModel.isLoading.value) "Завантаження..." else "Увійти")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(onClick = { viewModel.register(username, password) }, modifier = Modifier.fillMaxWidth()) {
                Text("Зареєструватися")
            }

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(viewModel.errorMessage.value, color = Color.Red)
            }
        } else {
            Text("Привіт, ${viewModel.currentUser.value}!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Button(onClick = { viewModel.logout() }) { Text("Вийти") }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Історія замовлень", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            LazyColumn {
                items(viewModel.orders) { order ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Замовлення №${order.id}", fontWeight = FontWeight.Bold)
                            Text("Сума: $${order.total}")
                            Text("Статус: ${order.status}", color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}