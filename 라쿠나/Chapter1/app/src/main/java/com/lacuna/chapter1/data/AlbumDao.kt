package com.lacuna.chapter1.data

import androidx.room.*

@Dao
interface AlbumDao {

    @Insert
    fun insert(album: TodayMusic)

    @Update
    fun update(album: TodayMusic)

    @Delete
    fun delete(album: TodayMusic)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<TodayMusic>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): TodayMusic

    @Insert
    fun likeAlbum(like: Like)

    // LikeTable에 사용자 uid와 앨범 id를 비교하여 있는지 확인하여 좋아요를 눌렀는지 안눌렀는지 체크
    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId: String, albumId: Int) : Int?

    // 좋아요 취소 delete
    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId: String, albumId: Int)

    // 좋아요한 앨범 가져오기
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbums(userId : Int) : List<TodayMusic>
}