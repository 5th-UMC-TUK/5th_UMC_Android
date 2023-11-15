package com.hgh.flo_clone.db.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.hgh.flo_clone.db.entity.AlbumEntity
//import com.hgh.flo_clone.db.entity.SongEntity
//
//@Dao
//interface AlbumDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(song: AlbumEntity)
//
//    @Query("SELECT * FROM AlbumEntity")
//    fun getAll(): List<AlbumEntity>
//}