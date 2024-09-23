package com.example.provakotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.provakotlin.entities.Produto
import com.example.provakotlin.ui.theme.ProvaKotlinTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutMain()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCadastroProduto(navController: NavController, listaProduto: MutableList<Produto>) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cadastro de Produtos", modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Nome do Produto")
        TextField(value = nome, onValueChange = { nome = it }, label = { Text(text = "Insira aqui") })

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Categoria")
        TextField(value = categoria, onValueChange = { categoria = it }, label = { Text(text = "Insira aqui") })

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Preço")
        TextField(value = preco, onValueChange = { preco = it }, label = { Text(text = "Insira aqui") })

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Quantidade")
        TextField(value = quantidade, onValueChange = { quantidade = it }, label = { Text(text = "Insira aqui") })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (nome.isBlank() && categoria.isBlank() && preco.isBlank() && quantidade.isBlank()) {

                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()

            } else {

                val produto = Produto(nome, categoria, preco.toDouble(), quantidade.toDouble())

                listaProduto.add(produto)
                navController.navigate("listaProdutos")
            }
        }) {
            Text(text = "Enviar")
        }
    }
}

@Composable
fun LayoutListaProdutos(navController : NavController, produtos: List<Produto>) {

    LazyColumn(modifier = Modifier.fillMaxWidth()){

        items(produtos){
            produto ->

            Column(
                modifier = Modifier.fillMaxSize()

            ) {
                Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Produto: ${produto.nome} Quantidade: (${produto.quantidade})")

                Button(onClick = {

                    val produtoJson = Produto.toJson(produto)

                    navController.navigate("detalhes/$produtoJson")


                }) {

                    Text(text = "Ver detalhes")
                }
            }
            

        }

    }



}

@Composable
fun LayoutDetalhesProdutos(navController : NavController, produto: Produto) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(text = "Detalhes do Produtos", modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Produto: ${produto.nome}" +
                "\n Quantidade: (${produto.categoria})"+
                "\n Quantidade: (${produto.preco})"+
                "\n Quantidade: (${produto.quantidade})")


        Button(onClick = {

            navController.navigate("cadastro")

        }) {
            Text(text = "Voltar a tela inicial")
        }

        Button(onClick = {

            navController.popBackStack()

        }) {
            Text(text = "Voltar a tela anterior ")
        }
    }



}

@Composable
fun LayoutMain() {

    val navController = rememberNavController()
    val listaProduto = remember { mutableStateListOf<Produto>() }

    NavHost(navController = navController, startDestination = "cadastro") {
        composable("cadastro") {
            LayoutCadastroProduto(navController, listaProduto)
        }

        composable("listaProdutos") {
            LayoutListaProdutos(navController, listaProduto)
        }

        composable("detalhes/{produtoJson}"){ backStackEntry ->

            val produtoJson = backStackEntry.arguments?.getString("produtoJson")
            val produtoResp = Produto.fromJson(produtoJson)

            LayoutDetalhesProdutos(navController, produtoResp)
        }

    }
}

