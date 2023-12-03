package com.lacuna.udemy.retrofitex

data class GithubResponse (
    val id : Int,
    val node_id : String,
    val name : String,
    val fullName : String,
    val private : Boolean,
    val owner: Owner
)

