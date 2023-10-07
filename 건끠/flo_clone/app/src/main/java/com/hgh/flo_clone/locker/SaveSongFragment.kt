package com.hgh.flo_clone.locker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.R
import com.hgh.flo_clone.adapter.SaveMusicRVAdapter
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.FragmentSaveSongBinding
import java.util.Collections

class SaveSongFragment : Fragment() {
    lateinit var binding: FragmentSaveSongBinding

    val adapter by lazy { SaveMusicRVAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSaveSongBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyDataList = dummyData()
        binding.saveSongRV.adapter = adapter
        adapter.setList(dummyDataList)
        adapter.deleteClickListener = {
            dummyDataList.remove(it)
            adapter.setList(dummyDataList)
        }
        binding.EditBtn.setOnClickListener {
            adapter.setList(dummyDataList)
        }


        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val formPos: Int = viewHolder.adapterPosition
                val toPos: Int = target.adapterPosition
                dummyDataList[formPos] = dummyDataList[toPos].also { dummyDataList[toPos] = dummyDataList[formPos] }
                adapter.swapList(formPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dummyDataList.removeAt(viewHolder.layoutPosition)
                adapter.setList(dummyDataList)
            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = (itemView.bottom - itemView.top).toFloat()
                    val width = height / 4
                    val paint = Paint()
                    if (dX < 0) { //dX는 터치한 지점이 0 임 <- 왼쪽으로 swipe했을때
                        paint.color = Color.parseColor("#ff0000")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, paint)
                        icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.baseline_delete_24
                        )?.toBitmap()!!
                        val iconDst = RectF(
                            itemView.right.toFloat() - 3 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, iconDst, null)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.saveSongRV)
    }

    fun dummyData(): MutableList<MusicModel> {
        return mutableListOf(
            MusicModel("Attention", "NewJens"),
            MusicModel("Hype boy", "NewJens"),
            MusicModel("Cookie", "NewJens"),
            MusicModel("ETA", "NewJens"),
            MusicModel("Dance Hall", "Mrs.Green Apple"),
            MusicModel("Zutto Zutto Zutto", "syakai"),
            MusicModel("왜 그래", "김현철"),
            MusicModel("Spicy", "aespa"),
            MusicModel("Boy", "king Gnu"),
            MusicModel("Vinyl", "King Gnu"),
            MusicModel("차라의 숲", "러브홀릭"),
            MusicModel("꿈과 책과 힘과 벽", "잔나비"),
            MusicModel("밤의 공원", "잔나비"),
            MusicModel("7.1oz", "Suda Masaki"),
            MusicModel("Bye by me", "Vaundy"),
            MusicModel("Tokyo Flash", "Vaundy"),
            MusicModel("붐바야", "BLACK PINK"),
            MusicModel("Ling Ling", "검정치마"),
        )
    }

}