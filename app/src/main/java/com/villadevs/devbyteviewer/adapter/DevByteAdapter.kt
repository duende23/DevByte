package com.villadevs.devbyteviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.villadevs.devbyteviewer.databinding.DevbyteListItemBinding
import com.villadevs.devbyteviewer.domain.DevByteVideo

class DevByteAdapter(private val onItemClicked: (DevByteVideo) -> Unit) :
    ListAdapter<DevByteVideo, DevByteAdapter.VideoListViewHolder>(DiffCallback) {

    /**
     * The videos that our Adapter will show
     */
    var videos: List<DevByteVideo> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val adapterLayout =
            DevbyteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        val currentVideo = getItem(position)
        holder.bind(currentVideo)

        holder.itemView.setOnClickListener {
            onItemClicked(currentVideo)
        }
    }


    class VideoListViewHolder(private var binding: DevbyteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(devByteVideo: DevByteVideo) {
            /* binding.tvItemName.text = item.itemName
             //binding.tvItemPrice.text = item.itemPrice.toString()
             binding.tvItemPrice.text = item.getFormattedPrice()
             binding.tvItemQuantity.text = item.itemQuantity.toString()*/

            binding.apply {
                tvTitle.text = devByteVideo.title
                tvTitle.text = devByteVideo.shortDescription


              ivVideoThumbnail.load(
                    devByteVideo.thumbnail.toUri().buildUpon().scheme("https").build()
                )

            }



        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DevByteVideo>() {
            override fun areItemsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
                //return oldItem.id == newItem.id
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
                return oldItem == newItem
            }

        }
    }


}