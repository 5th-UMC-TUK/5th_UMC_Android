package com.hgh.flo_clone.db
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import com.hgh.flo_clone.db.dao.AlbumDao
//import com.hgh.flo_clone.db.dao.SongDao
//import com.hgh.flo_clone.db.entity.AlbumEntity
//import com.hgh.flo_clone.db.entity.SongEntity
//
//@Database(
//    entities = [SongEntity::class , AlbumEntity::class],
//    version = 1,
//    exportSchema = false
//)
//abstract class ApplicationDB: RoomDatabase(){
//
//    companion object{
//        const val DB_NAME = "ApplicationDB.db"
//    }
//
//    abstract fun SongDao() : SongDao
//    abstract fun AlbumDao() : AlbumDao
//}