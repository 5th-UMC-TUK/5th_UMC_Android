package com.example.chapter1.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {
    @Insert
    fun insert(song: Album)

    @Update
    fun update(song: Album)

    @Delete
    fun delete(song: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAllAlbum(): List<Album>

    @Query("SELECT * FROM AlbumTable where id = :id")
    fun getAlbum(id: Int): Album

    @Query("SELECT * FROM AlbumTable where isLike =:like")
    fun getLikeAlbums(like: Boolean): List<Album>
}