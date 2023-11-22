package com.hgh.flo_clone.server.repository

import android.widget.Toast
import com.hgh.flo_clone.server.network.ApiService
import com.hgh.flo_clone.server.response.album.Album
import com.hgh.flo_clone.server.response.albumin.Result
import com.hgh.flo_clone.server.response.song.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultApiRepository(
    private val productApiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : ApiRepository {
    override suspend fun getSongList(): List<Song>? = withContext(ioDispatcher) {
        val response = productApiService.getSongApi()
        if (response.isSuccessful) {
            response.body()?.result?.songs
        } else {
            null
        }
    }

    override suspend fun getAlbumList(): List<Album>? = withContext(ioDispatcher) {
        val response = productApiService.getAlbumApi()
        if (response.isSuccessful) {
            response.body()?.result?.albums
        } else {
            null
        }
    }


    override suspend fun getAlbumInList(index: Int): List<Result>? = withContext(ioDispatcher){
        val response = productApiService.getAlbumInApi(index)
        if (response.isSuccessful){
            response.body()?.result
        }else{
            null
        }
    }
}