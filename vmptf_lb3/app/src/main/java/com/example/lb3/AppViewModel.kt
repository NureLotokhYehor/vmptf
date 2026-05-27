package com.example.lb3


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    val products = mutableStateListOf<Product>()
    val recommendations = mutableStateListOf<Product>()
    val cart = mutableStateListOf<Product>()
    val wishlist = mutableStateListOf<Product>()
    val orders = mutableStateListOf<OrderResponse>()

    var token = mutableStateOf<String?>(null)
    var currentUser = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                products.clear()
                products.addAll(RetrofitClient.apiService.getProducts())
            } catch (e: Exception) {
                errorMessage.value = "Помилка завантаження товарів"
            }
        }
    }

    fun loadRecommendations() {
        viewModelScope.launch {
            token.value?.let { t ->
                try {
                    recommendations.clear()
                    recommendations.addAll(RetrofitClient.apiService.getRecommendations("Bearer $t"))
                } catch (e: Exception) {  }
            }
        }
    }
    fun login(user: String, pass: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitClient.apiService.login(AuthRequest(user, pass))
                token.value = response.token
                currentUser.value = response.username
                errorMessage.value = ""
                loadRecommendations()
                loadOrders()
            } catch (e: Exception) {
                errorMessage.value = "Невірний логін або пароль"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun logout() {
        token.value = null
        currentUser.value = null
        recommendations.clear()
        orders.clear()
    }

    fun toggleWishlist(product: Product) {
        if (wishlist.contains(product)) wishlist.remove(product) else wishlist.add(product)
    }

    fun checkout() {
        viewModelScope.launch {
            val currentToken = token.value
            if (currentToken == null) {
                errorMessage.value = "Увійдіть для оформлення замовлення"
                return@launch
            }
            try {
                val items = cart.map { OrderItem(it.id, it.price) }
                val total = cart.sumOf { it.price }
                RetrofitClient.apiService.createOrder("Bearer $currentToken", OrderRequest(items, total))
                cart.clear()
                loadOrders()
                loadRecommendations()
                errorMessage.value = "Замовлення успішно оформлено!"
            } catch (e: Exception) {
                errorMessage.value = "Помилка оформлення"
            }
        }
    }

    fun register(user: String, pass: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                RetrofitClient.apiService.register(AuthRequest(user, pass))
                errorMessage.value = "Реєстрація успішна. Виконуємо вхід"

                login(user, pass)
            } catch (e: Exception) {
                errorMessage.value = "Помилка реєстрації. Можливо, такий користувач вже існує"
            } finally {
                isLoading.value = false
            }
        }
    }
    fun loadOrders() {
        viewModelScope.launch {
            token.value?.let { t ->
                try {
                    orders.clear()
                    orders.addAll(RetrofitClient.apiService.getOrders("Bearer $t"))
                } catch (e: Exception) { }
            }
        }
    }
}