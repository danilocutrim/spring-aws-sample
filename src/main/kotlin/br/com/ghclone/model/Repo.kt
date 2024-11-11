package br.com.ghclone.model

data class Repo(
    val id: String,
    val name: String,
    val description: String?=null,
    val contents: List<String>
)
