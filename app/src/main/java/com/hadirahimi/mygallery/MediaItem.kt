package com.hadirahimi.mygallery

data class MediaItem(
    val id : Long,
    val filePath : String,
    val displayName:String,
    val mimeType:String
){
    fun isImage(): Boolean = mimeType.startsWith("image")
    fun isVideo(): Boolean = mimeType.startsWith("video")
}