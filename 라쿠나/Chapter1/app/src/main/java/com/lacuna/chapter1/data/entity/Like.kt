package com.lacuna.chapter1.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeTable")
data class Like(
    var userId : String,
    var albumId : Int
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
