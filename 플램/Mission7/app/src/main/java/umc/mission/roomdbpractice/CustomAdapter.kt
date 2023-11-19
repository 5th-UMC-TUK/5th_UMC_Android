package umc.mission.roomdbpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mission.roomdbpractice.databinding.ItemLayoutBinding

class CustomAdapter(private var list: List<Profile>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(profile: Profile){
            //binding.itemTv1.text = profile.id.toString()
            binding.itemTv2.text = profile.name
            binding.itemTv3.text = profile.age
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = list[position]
        holder.bind(profile)
    }
}