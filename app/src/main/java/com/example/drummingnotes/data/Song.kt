package com.example.drummingnotes.data

data class Song(
    var songId: String = "",
    val title: String = "",
    val color: String = "",
    val drummer: String = "",
    val subtitle: String = "",
    val album: String = "",
    val difficulty: String = "",
    val category: String = "",
    val duration: String = "",
    val description: String = "",
    val youtubelink: String = "",
    var isFavorited: Boolean = false
)