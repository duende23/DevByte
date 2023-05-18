package com.villadevs.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.villadevs.devbyteviewer.database.VideoRoomDatabase
import com.villadevs.devbyteviewer.database.asDomainModel
import com.villadevs.devbyteviewer.domain.DevByteVideo
import com.villadevs.devbyteviewer.network.DevByteNetwork
import com.villadevs.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class VideosRepository (val database: VideoRoomDatabase){

   // val videos: LiveData<List<DevByteVideo>> = database.videoDao.getVideos()
    //val videos: Flow<List<DevByteVideo>> = database.videoDao().getVideos()

    val videos: LiveData<List<DevByteVideo>> =(database.videoDao().getVideos()).map {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = DevByteNetwork.devbytes.getPlaylist()
            database.videoDao().insertAll(playlist.asDatabaseModel())
        }
    }
}