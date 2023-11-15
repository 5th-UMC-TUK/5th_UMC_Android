package com.hgh.flo_clone.db.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.hgh.flo_clone.db.entity.SongEntity
//
//@Dao
//interface SongDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(song:SongEntity )
//
//    @Query("SELECT * FROM SongEntity")
//    fun getAll(): List<SongEntity>
//
//    @Query("UPDATE songEntity SET isLike=:isLike WHERE id = :id")
//    fun likeClick(id: Int, isLike : Boolean)
//
//    @Query("DELETE FROM SongEntity WHERE id=:songId")
//    fun delete(songId: Int)
//
//}