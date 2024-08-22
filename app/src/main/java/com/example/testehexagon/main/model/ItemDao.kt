package com.example.testehexagon.main.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: Long): Item?

    @Insert
    suspend fun inserir(item: Item)

    @Update
    suspend fun atualizar(item: Item)

    @Query("UPDATE items SET ativo = 0 WHERE id = :id")
    suspend fun desativar(id: Long)

    @Query("UPDATE items SET ativo = 1 WHERE id = :id")
    suspend fun ativar(id: Long)

    @Query("SELECT * FROM items WHERE ativo = 1")
    suspend fun listarAtivos(): List<Item>

    @Query("SELECT * FROM items WHERE ativo = 0")
    suspend fun listarInativos(): List<Item>
}