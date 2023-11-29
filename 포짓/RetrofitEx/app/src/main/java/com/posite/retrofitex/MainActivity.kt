package com.posite.retrofitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.posite.retrofitex.network.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val repository = GithubRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRepo()
    }

    private fun myRepo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repos = CoroutineScope(Dispatchers.IO).async {
                repository.getAllRepos("posite")
            }.await()
            repos?.let {
                it.forEach { repo-> Log.d("repo", "repo name: ${repo.name}") }
            }
        }
    }
}