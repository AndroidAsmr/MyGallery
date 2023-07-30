package com.hadirahimi.mygallery

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat

object PermissionManager
{
    const val REQUEST_CODE_PERMISSION = 100
    
    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun hasPermissions(activity:Activity):Boolean{
        for (permission in REQUIRED_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
    fun requestPermission(activity : Activity){
        ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION)
    }
}









