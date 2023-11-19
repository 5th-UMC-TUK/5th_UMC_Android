package com.example.chapter1.model

import com.example.chapter1.db.Album
import com.example.chapter1.db.Song

data class TitleAlbumModel(
    var album: Album,
    var song: MutableList<Song>
)