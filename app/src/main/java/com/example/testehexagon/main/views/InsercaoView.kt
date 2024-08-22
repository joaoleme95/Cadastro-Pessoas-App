package com.example.testehexagon.main.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.testehexagon.main.model.Item
import com.example.testehexagon.main.viewmodels.ItemViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ItemInsertScreen(viewModel: ItemViewModel, navHostController: NavHostController) {
    var nome by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> if (uri != null) fotoUri = uri.toString() }
    )

    Scaffold(
        topBar = {
            Text(
                text = "Novo Cadastro",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(24.dp)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = dataNascimento,
                    onValueChange = {
                        dataNascimento = it
                    },
                    label = { Text("Data de Nascimento (dd/mm/yyyy)") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = cpf,
                    onValueChange = { newValue ->
                        // Permitir apenas números
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            cpf = newValue
                        }
                    },
                    label = { Text("CPF (somente números)") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text(text = "Selecionar Foto")
                    }

                    if (fotoUri.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = fotoUri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        if (nome.isBlank() || dataNascimento.isBlank() || cpf.isBlank() || cidade.isBlank() || fotoUri.isBlank()) {
                            errorMessage = "Por favor, preencha todos os campos obrigatórios."
                        } else {
                            val newItem = Item(
                                id = 0,
                                nome = nome,
                                dataNascimento = dataNascimento,
                                cpf = cpf,
                                cidade = cidade,
                                fotoUri = fotoUri,
                                ativo = true
                            )
                            viewModel.inserir(newItem)
                            navHostController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Inserir")
                }
            }
        }
    )
}
