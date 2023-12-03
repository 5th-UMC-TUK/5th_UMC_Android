package umc.mission.retrofitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var networkModule = NetworkModule()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repo()
    }
    private fun repo(){
        CoroutineScope(Dispatchers.IO).launch {
            val repos = CoroutineScope(Dispatchers.IO).async {
                networkModule.getAllRepos("kyujin0911")
            }.await()
            repos?.let{
                it.forEach { repo -> Log.d("repo", "repo name: ${repo.name}") }
            }
        }
    }
}