package com.example.prova_kotlin.entities

class Estoque {

    companion object {

        fun calcularValorTotalEstoque(lista: List<Produto>): Double{
            var sum = 0.0
            lista.forEach{it -> sum += it.preco * it.quantidade }
            return sum
        }

        fun calcularQuantidadeTotalEstoque(lista: List<Produto>): Double{
            var sum = 0.0
            lista.forEach{it -> sum += it.quantidade }
            return sum
        }

    }


}