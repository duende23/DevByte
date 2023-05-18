

package com.villadevs.devbyteviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.villadevs.devbyteviewer.domain.DevByteVideo


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */


/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class Video constructor(
        @PrimaryKey
        val url: String,
        val updated: String,
        val title: String,
        val description: String,
        val thumbnail: String)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<Video>.asDomainModel(): List<DevByteVideo> {
        return map {
                DevByteVideo(
                        url = it.url,
                        title = it.title,
                        description = it.description,
                        updated = it.updated,
                        thumbnail = it.thumbnail)
        }
}
