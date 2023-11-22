package com.hgh.flo_clone.server.repository

import com.hgh.flo_clone.server.response.album.Album
import com.hgh.flo_clone.server.response.albumin.Result
import com.hgh.flo_clone.server.response.song.Song

interface ApiRepository {

    suspend fun getSongList():List<Song>?

    suspend fun getAlbumList(): List<Album>?

    suspend fun getAlbumInList(index : Int): List<Result>?
}