package com.example.quiz.ch1_2


data class Order(
    val orderId: Int,
    val customerId: Int,
    val items: List<String>,
    val totalAmount: Int,
    val status: String,
    val isPriority: Boolean
)

data class ProcessedOrder(
    val orderId: Int,
    val customerId: Int,
    val finalAmount: Int,
    val message: String
)
