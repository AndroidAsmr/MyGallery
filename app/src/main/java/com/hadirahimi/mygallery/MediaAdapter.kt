package com.hadirahimi.mygallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.hadirahimi.mygallery.databinding.ItemGalleryBinding

class MediaAdapter : ListAdapter<MediaItem,MediaAdapter.MediaViewHolder>(MediaDiffCallback()){
    
    inner class MediaViewHolder(private val binding : ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mediaItem : MediaItem){
            Glide.with(itemView)
                .load(mediaItem.filePath)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageview)
            
            if (mediaItem.isImage())
            {
                binding.tvDuration.visibility = View.GONE
            }else if (mediaItem.isVideo())
            {
                binding.tvDuration.visibility = View.VISIBLE
                binding.tvDuration.text = getVideoDurationAsString(mediaItem.filePath)
            }
            binding.root.setOnClickListener {
                 onItemClickListener?.let {
                     it(mediaItem)
                 }
            }
        }
    }
    private var onItemClickListener : ((MediaItem) -> Unit?)? = null
    fun setOnItemClickListener(listener : (MediaItem)-> Unit)
    {
        onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MediaViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGalleryBinding.inflate(inflater,parent,false)
        return MediaViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder : MediaViewHolder , position : Int)
    {
        val mediaItem = getItem(position)
        holder.bind(mediaItem)
    }
}






