//package com.lacuna.floclone.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.lacuna.chapter1.data.TodayMusic
//import com.lacuna.chapter1.databinding.ItemTodayMusicBinding
//
//class TodayMusicAdapter(private val todayMusicList: ArrayList<TodayMusic>): RecyclerView.Adapter<TodayMusicAdapter.TodayMusicViewHolder>(){
//
//    interface OnItemClickListener {
//        fun onItemClick(todayMusic: TodayMusic)
//        fun onPlayAlbum(todayMusic: TodayMusic)
//    }
//
//    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
//    private lateinit var itemClickListener: OnItemClickListener
//    fun setMyItemClickListener(onItemClickListener: OnItemClickListener){
//        itemClickListener = onItemClickListener
//    }
//    /*onCreateViewHolder : 해당 뷰그룹의 LayoutInflater로 inflate한 뷰홀더 레이아웃을 뷰홀더에 넣어준다
//    TodayMusicViewHolder : 아이템뷰 객체 생성
//    onBindViewHolder : 뷰홀더에 데이터를 넣어준다
//    getItemCount : 아이템뷰 갯수를 반환*/
//
//    fun addItem(todayMusic: TodayMusic){
//        todayMusicList.add(todayMusic)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TodayMusicAdapter.TodayMusicViewHolder {
//        val binding =  ItemTodayMusicBinding.inflate(
//            LayoutInflater.from(parent.context), // layoutInflater 를 넘기기위해 함수 사용, ViewGroup 는 View 를 상속하고 View 는 이미 Context 를 가지고 있음
//            parent, // 부모(리싸이클러뷰 = 뷰그룹)
//            false   // 리싸이클러뷰가 attach 하도록 해야함 (우리가 하면 안됨)
//        )
//        return TodayMusicViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: TodayMusicAdapter.TodayMusicViewHolder, position: Int) {
//        holder.bind(todayMusicList[position]) // 해당 포지션의 데이터의 값을 ViewHolder에 bind 함수에 넘겨줌
//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(todayMusicList[position])
//        }
//        holder.binding.itemTodayMusicPlayIv.setOnClickListener {
//            itemClickListener.onPlayAlbum(todayMusicList[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return todayMusicList.size
//    }
//
//    inner class TodayMusicViewHolder(val binding: ItemTodayMusicBinding) : RecyclerView.ViewHolder(binding.root) {
//        //        init {
////            binding.root.setOnClickListener {
////                val pos = adapterPosition
////                if(pos != RecyclerView.NO_POSITION){
////                    val clickedItem = todayMusicList[pos]
////
////                    Log.d("Click", "$pos")
////                    Log.d("Click", "$clickedItem")
////                }
////
////            }
////        }
//        fun bind(todayMusic: TodayMusic) {
//            binding.itemTodayMusicAlbumIv.setImageResource(todayMusic.coverImg!!)
//            binding.itemTodayMusicTitleTv.text = todayMusic.title
//            binding.itemTodayMusicSingerTv.text = todayMusic.singer
//        }
//    }
//}