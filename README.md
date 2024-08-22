# Relatório do Projeto

## Funcionamento do App

[Assista a demonstração do app aqui](https://youtu.be/W3PfoPLEG9A)

## Descrição do Projeto

Este projeto é uma aplicação Android que utiliza armazenamento local para gerenciar itens. As funcionalidades principais incluem:

1. Inserir
2. Editar
3. Desativar
4. Ativar
5. Listar

Cada item possui as seguintes propriedades:
- Nome
- Data de Nascimento
- CPF
- Cidade
- Foto (Galeria)
- Ativo

A tela principal exibe uma lista de itens cadastrados ativos, mostrando a foto, o nome e a idade. O usuário pode acessar as ações de inserir, editar e desativar. Também há uma tela adicional para listar itens inativos, permitindo a reativação dos mesmos.

## Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose
- **Arquitetura**: MVVM
- **Persistência de Dados**: Room
- **Gerenciamento de Dependências**: Dagger Hilt
- **Navegação**: Jetpack Navigation Compose

## Configuração do Projeto (`build.gradle`)

### Dependências

- **Room**: Utilizado para persistência de dados locais.
- **Dagger Hilt**: Para injeção de dependências.
- **Coil**: Biblioteca para carregamento de imagens.
- **Navigation Compose**: Para navegação entre telas.
  
## `MainActivity`

A classe `MainActivity` é a atividade principal do aplicativo e serve como o ponto de entrada para a interface do usuário.

### Navegação (`AppNavGraph`)

A função `AppNavGraph` define a estrutura de navegação do aplicativo utilizando o Jetpack Navigation Compose.

## Tela (`ItemListScreen`)

A tela ItemListScreen é responsável por exibir a lista de itens cadastrados que estão ativos. Ela permite ao usuário visualizar informações essenciais de cada item, bem como realizar ações como editar ou desativar.

### Coleta de dados
A lista de itens ativos é coletada do ItemViewModel utilizando collectAsState(), que observa as alterações na lista.

### Elementos

O componente **Scaffold** fornece a estrutura básica da tela, incluindo uma barra superior (topBar) e uma barra inferior (bottomBar).

- **TopBar:** Exibe um texto que varia conforme a quantidade de itens: se a lista estiver vazia, mostra "Nenhuma pessoa cadastrada"; caso contrário, "Pessoas cadastradas ativas".

- **BottomBar:** Botão que navega para a tela de inserção de novos itens, e botão que navega para a tela que lista os itens inativos.

### Componente `ItemRow`

O componente ItemRow é responsável por exibir as informações de cada item individualmente, com a opção de editar ou desativar, na mesma tela.

## Tela (`ItemInsertScreen`)

Após clicar no botão `Inserir`, o usuário será levado a tela de inserção de itens.

A tela `ItemInsertScreen` é responsável por permitir ao usuário inserir um novo item na lista. Nesta tela, o usuário preenche informações como nome, data de nascimento, CPF, cidade e pode selecionar uma foto. Após preencher todos os campos obrigatórios, o usuário pode inserir o novo item.

### Funcionalidades

- **Variáveis de Estado:** São utilizadas variáveis de estado para capturar os valores dos campos do formulário, como nome, dataNascimento, cpf, cidade e fotoUri.

- **Image Picker:** O imagePickerLauncher é utilizado para permitir ao usuário selecionar uma imagem da galeria.

- **Campos de Texto:** O formulário possui campos de texto para o nome, data de nascimento, CPF e cidade. O campo de CPF permite apenas a inserção de números.

- **Seleção de Foto:** O botão "Selecionar Foto" abre a galeria de imagens, e a imagem selecionada é exibida em um círculo ao lado do botão.

- **Mensagem de Erro:** Se algum campo obrigatório não for preenchido, uma mensagem de erro é exibida abaixo dos campos.

- **Botão de Inserção:** Ao clicar no botão "Inserir", o aplicativo valida se todos os campos foram preenchidos e, em caso afirmativo, insere o novo item na lista e retorna à tela anterior.

## Tela (`ItemEditScreen`)

Após clicar no botão `Editar`, o usuário será levado a tela de edição de itens.

A tela `ItemEditScreen` permite a edição dos detalhes de um item existente no aplicativo.

### Observação e Carregamento de Dados:

`itemState`: Observa o estado do item a ser editado, usando observeAsState(), para manter a UI atualizada com os dados do item.
  
`LaunchedEffect(itemId)`: Carrega os dados do item correspondente ao itemId assim que a tela é iniciada, chamando viewModel.getItemById(itemId).
  
`LaunchedEffect(itemState.value):` Atualiza os estados locais (nome, dataNascimento, etc.) quando os dados do item são carregados.

Se todos os campos estiverem preenchidos corretamente, o item é atualizado usando a função atualizar do viewModel e a navegação retorna à tela anterior.

## Tela (`InactiveListScreen`)

Após clicar no botão `Pessoas Inativas`, o usuário será levado a tela de itens inativos.

Assim como na tela `ItemListScreen`, a lista de itens inativos é coletada do ItemViewModel utilizando collectAsState(), que observa as alterações na lista.

Com apenas um botão para reativar o item selecionado, e outro botão para voltar a tela inicial.

## Botões (`Desativar`) e (`Reativar`)

Esses botões chamam suas respectivas funções no viewModel, que mudam o status `ativo` do item, e atualizam a lista.

## Resumo da Arquitetura

![image](https://github.com/user-attachments/assets/47c01847-9dce-45ce-bc81-2de4e2978586)

A arquitetura apresentada segue o padrão MVVM (Model-View-ViewModel):

- **Model:** Lida com a camada de dados, representando as entidades e acessando o banco de dados por meio dos DAOs.
  
- **ViewModel:** Atende como uma ponte entre a UI e os dados, gerenciando o estado e a lógica de negócios da aplicação.
  
- **View:** Compostas em arquivos separados, as Views lidam com a interface do usuário, exibindo dados e enviando interações para o ViewModel.

- **MainActivity:** Esta é a atividade principal do aplicativo, que configura a navegação entre as diferentes Views e inicializa a lógica do ViewModel.
  
Essa estrutura modularizada facilita a separação de responsabilidades e melhora a organização do código, facilitando a manutenção aplicativo.

### AppDatabase

Este é o banco de dados principal do aplicativo. É responsável por fornecer as instâncias dos DAOs.

### Item 

Esta é a entidade que representa a tabela no banco de dados. Cada instância da classe Item corresponde a uma linha na tabela. Contém campos como id, nome, dataNascimento, cpf, cidade, fotoUri, e ativo.

### ItemDao

Define os métodos para acessar e manipular os dados da tabela Item, como inserir, atualizar, deletar e buscar itens.

### ItemRepository

O repositório atua como uma camada intermediária entre o DAO e o ViewModel. Ele encapsula a lógica de acesso aos dados e fornece para o ViewModel.

### ItemViewModel

O ViewModel é responsável por gerenciar os dados para as Views e manter o estado durante mudanças de configuração. Ele se comunica com o ItemRepository para obter os dados necessários e os expõe para a interface do usuário. Além disso, ele pode conter lógica que manipula os dados antes de passá-los para a UI.

**------------------------------------------------------------------------------------------------------------------------------------------------------------------**





