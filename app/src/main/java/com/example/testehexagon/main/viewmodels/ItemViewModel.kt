package com.example.testehexagon.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testehexagon.main.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _ativosFlow = MutableStateFlow<List<Item>>(emptyList())
    val ativosFlow: StateFlow<List<Item>> = _ativosFlow

    private val _inativosFlow = MutableStateFlow<List<Item>>(emptyList())
    val inativosFlow: StateFlow<List<Item>> = _inativosFlow

    private val _item = MutableLiveData<Item?>()
    val item: LiveData<Item?> get() = _item

    init {
        // Inicializa com os itens ativos e inativos
        viewModelScope.launch {
            _ativosFlow.value = repository.listarAtivos()
            _inativosFlow.value = repository.listarInativos()
        }
    }

    fun getItemById(itemId: Long) {
        viewModelScope.launch {
            val retrievedItem = repository.getItemById(itemId)
            _item.value = retrievedItem
        }
    }

    fun inserir(item: Item) = viewModelScope.launch {
        repository.inserir(item)
        refreshAtivos()
    }

    fun atualizar(item: Item) = viewModelScope.launch {
        repository.atualizar(item)
        refreshAtivos()
        refreshInativos()
    }

    fun desativar(id: Long) = viewModelScope.launch {
        repository.desativar(id)
        refreshAtivos()
        refreshInativos()
    }

    fun ativar(id: Long) = viewModelScope.launch {
        repository.ativar(id)
        refreshAtivos()
        refreshInativos()
    }

    private suspend fun refreshAtivos() {
        _ativosFlow.value = repository.listarAtivos()
    }

    private suspend fun refreshInativos() {
        _inativosFlow.value = repository.listarInativos()
    }

    fun listarAtivos(): StateFlow<List<Item>> = ativosFlow

    fun listarInativos(): StateFlow<List<Item>> = inativosFlow
}