package com.example.drummingnotes.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class DrummingNotesViewModel: ViewModel() {
    private val db = Firebase.firestore
    val songData = mutableStateListOf<Song>()
    init {
        fetchDatabaseData()
    }
    private fun fetchDatabaseData() {
        db.collection("songs")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val song = data.toObject(Song::class.java)
                    if (song != null) {
                        song.songId= data.id
                        songData.add(song)
                    }
                }
            }
    }
    fun updateSong(song: Song) {
        db.collection("songs")
            .document(song.songId)
            .set(song)
    }
}

