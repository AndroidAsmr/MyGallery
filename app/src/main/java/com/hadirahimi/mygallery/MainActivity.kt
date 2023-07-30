package com.hadirahimi.mygallery

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hadirahimi.mygallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapterMedia : MediaAdapter
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapterMedia = MediaAdapter()
        
        if (PermissionManager.hasPermissions(this))
        {
            loadInRecycler()
        }
        else PermissionManager.requestPermission(this)
        
        adapterMedia.setOnItemClickListener { media ->
            
            val intent = Intent(Intent.ACTION_VIEW)
            val fileUri = Uri.parse(media.filePath)
            intent.setDataAndType(fileUri , media.mimeType)
            startActivity(intent)
            
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode : Int , permissions : Array<out String> , grantResults : IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if (requestCode == PermissionManager.REQUEST_CODE_PERMISSION)
        {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })
            {
                loadInRecycler()
            }
            else
            {
                Toast.makeText(
                    this@MainActivity ,
                    "permissions are required to access media items." ,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private fun loadInRecycler()
    {
        adapterMedia.submitList(loadMediaItems())
        binding.recyclerView.apply {
            adapter = adapterMedia
            layoutManager = GridLayoutManager(this@MainActivity , 3)
        }
    }
    
    private fun loadMediaItems() : List<MediaItem>
    {
        val mediaList = mutableListOf<MediaItem>()
        
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID ,
            MediaStore.Files.FileColumns.DATA ,
            MediaStore.Files.FileColumns.DISPLAY_NAME ,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val selection =
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString() ,
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
        
        val contentResolver : ContentResolver = contentResolver
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external") ,
            projection ,
            selection ,
            selectionArgs ,
            sortOrder
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val filePathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val displayNameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val mimeTypeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
            while (it.moveToNext())
            {
                val id = it.getLong(idColumn)
                val filePath = it.getString(filePathColumn)
                val displayName = it.getString(displayNameColumn)
                val mimeType = it.getString(mimeTypeColumn)
                
                val mediaItem = MediaItem(id,filePath,displayName,mimeType)
                mediaList.add(mediaItem)
            }
        }
        return mediaList
    }
    
}


















