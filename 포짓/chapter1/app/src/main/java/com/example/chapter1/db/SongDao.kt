package com.example.chapter1.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM SongTable")
    fun getAllSong(): List<Song>

    @Query("SELECT * FROM SongTable where id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike = :isLike WHERE id = :id")
    fun updateIsLike(id: Int, isLike: Boolean)

    @Query("SELECT * FROM SongTable WHERE isLike = :isLike")
    fun getLikedSong(isLike: Boolean): List<Song>
}