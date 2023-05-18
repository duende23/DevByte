

package com.villadevs.devbyteviewer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.villadevs.devbyteviewer.domain.DevByteVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("select * from video")
    fun getVideos(): LiveData<List<Video>>
    //fun getVideos(): Flow<List<DevByteVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<Video>)
}




