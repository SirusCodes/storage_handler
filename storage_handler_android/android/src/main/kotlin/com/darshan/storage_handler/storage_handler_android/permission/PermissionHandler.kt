package com.darshan.storage_handler.storage_handler_android.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import io.flutter.plugin.common.PluginRegistry

class PermissionHandler :
        PluginRegistry.RequestPermissionsResultListener {
    companion object {
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100
    }

    private var successResponse: PermissionSuccessResponse? = null
    private var activity: Activity? = null

    fun requestWritePermission(successResponse: PermissionSuccessResponse, activity: Activity) {
        Log.d("requestWritePermission", "onRequestPermissionsResult: $successResponse ")

        this.successResponse = successResponse
        this.activity = activity

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE
            )
        else successResponse.onSuccess(PermissionResult.GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Boolean {
        val permissionGranted: Boolean = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        Log.d(permissionGranted.toString(), "onRequestPermissionsResult: ${this.successResponse} ")
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (permissionGranted) {
                // When permission is granted
                this.successResponse?.onSuccess(PermissionResult.GRANTED)
            } else if (!showRequestPermissionRational(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // When permission is denied forever
                this.successResponse?.onSuccess(PermissionResult.DENIED_FOREVER)
            } else {
                // When permission is denied
                this.successResponse?.onSuccess(PermissionResult.DENIED)
            }
        }
        return false
    }

    private fun showRequestPermissionRational(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)
    }

    interface PermissionSuccessResponse {
        fun onSuccess(permissionResult: PermissionResult)
    }
}