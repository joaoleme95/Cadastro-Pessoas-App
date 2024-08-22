package com.example.testehexagon.main.viewmodels

import com.example.testehexagon.main.model.Item
import com.example.testehexagon.main.model.ItemDao

class ItemRepository(private val itemDao: ItemDao) {
    suspend fun getItemById(itemId: Long): Item? {
        return itemDao.getItemById(itemId)
    }
    suspend fun inserir(item: Item) = itemDao.inserir(item)
    suspend fun atualizar(item: Item) = itemDao.atualizar(item)
    suspend fun desativar(id: Long) = itemDao.desativar(id)
    suspend fun ativar(id: Long) = itemDao.ativar(id)
    suspend fun listarAtivos() = itemDao.listarAtivos()
    suspend fun listarInativos() = itemDao.listarInativos()
}