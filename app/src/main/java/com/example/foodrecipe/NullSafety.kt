package com.example.foodrecipe

import javax.annotation.Nullable


data class User(
    val id: String,
    val name: String,
    val gender: String?
)

class TextView {

    var text: String = ""
}

fun main() {
    val user1 = User("1", "User1", "M")


    val textView = TextView()
    user1.gender?.let { g ->
        textView.text = g
    }

    if (user1.gender != null) {
        textView.text = user1.gender
    }

    textView.text = user1.gender ?: "un defined"

    val user2 = User("2", "User2", null)
}