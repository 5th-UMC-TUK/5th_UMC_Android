package umc.mission.roomdbpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import umc.mission.roomdbpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var list = ArrayList<Profile>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var customAdapter: CustomAdapter
    private lateinit var db: ProfileDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProfileDatabase.getInstance(this)!!
        CoroutineScope(Dispatchers.IO).launch {
            val savedContacts = db.profileDao().getAll()
            if (savedContacts.isNotEmpty()) {
                list.addAll(savedContacts)
                Log.d("list", "$list")
            }
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.mainProfileRv.layoutManager = layoutManager
        customAdapter = CustomAdapter(list)
        binding.mainProfileRv.adapter = customAdapter
        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                list.add(Profile("헤이", "24 ","0000"))
                db.profileDao().insert(Profile("헤이", "24 ","0000"))

                val list = db.profileDao().getAll()
                Log.d("Inserted PrimaryKey", list[list.size-1].id.toString())
            }
            customAdapter.notifyDataSetChanged()
        }
        binding.deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                list.clear()
                db.profileDao().deleteAllProfile()
            }
            customAdapter.notifyDataSetChanged()
        }
    }
}