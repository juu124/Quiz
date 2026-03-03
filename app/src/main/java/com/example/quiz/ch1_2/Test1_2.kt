package com.example.quiz.ch1_2

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

// 주문 영수증 출력 프로그램
fun main() = runBlocking {
    val orders = listOf(
        Order(1, 101, listOf("laptop", "Mouse"), 1_500_000, "pending", true),
        Order(2, 102, listOf("keyboard"), 80_000, "pending", false),
        Order(3, 103, listOf("Monitor", "Cable", "Stand"), 450_000, "cancelled", false),
        Order(4, 104, listOf("Headset"), 120_000, "pending", true),
        Order(5, 105, listOf("Chair"), 250_000, "pending", false),
        Order(6, 106, listOf("Desk", "Lamp"), 600_000, "pending", true)
    )

    orders.asFlow()
        .filter { it.status == "pending" }
        .map { order ->
            order.copy(totalAmount = order.totalAmount + 5000)
        }
        .transform {
            if (it.isPriority) {
                emit(it)
            } else if (it.totalAmount >= 30000) {
                emit(it)
            }
        }
        .collect { println(it) }
}