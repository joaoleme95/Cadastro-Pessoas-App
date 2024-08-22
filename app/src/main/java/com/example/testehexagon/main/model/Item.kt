package com.example.testehexagon.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val dataNascimento: String,
    val cpf: String,
    val cidade: String,
    val fotoUri: String,
    var ativo: Boolean
)