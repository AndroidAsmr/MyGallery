package com.hadirahimi.mygallery

import androidx.recyclerview.widget.DiffUtil

class MediaDiffCallback : DiffUtil.ItemCallback<MediaItem>()
{
    override fun areItemsTheSame(oldItem : MediaItem , newItem : MediaItem) : Boolean
    {
        return oldItem.id == newItem.id
    }
    
    override fun areContentsTheSame(oldItem : MediaItem , newItem : MediaItem) : Boolean
    {
        return oldItem == newItem
    }
    
}




