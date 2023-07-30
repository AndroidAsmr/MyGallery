package com.hadirahimi.mygallery

import android.media.MediaMetadataRetriever
import java.util.Locale

fun getVideoDurationAsString(filePath : String) : String{
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(filePath)
    
    val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong()?:0
    val durationSeconds = duration/1000
    
    val minutes = durationSeconds/60
    val seconds = durationSeconds % 60
    
    return String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds)
}