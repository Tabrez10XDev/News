package com.example.news.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class article(

    var name:String

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}