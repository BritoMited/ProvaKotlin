package com.example.prova_kotlin.entities

import com.google.gson.Gson

data class Produto(

    var nome: String,
    var categoria: String,
    var preco: Double,
    var quantidade: Double

) {
    companion object {
        private val gson = Gson()


        fun fromJson(json: String?): Produto {
            return gson.fromJson(json, Produto::class.java)
        }


        fun toJson(produto: Produto): String {
            return gson.toJson(produto)
        }
    }


}