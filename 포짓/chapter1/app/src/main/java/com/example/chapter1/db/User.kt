package com.example.chapter1.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey
    var id: String = "",
    var endpoint: String = "",
    var password: String = ""
)
