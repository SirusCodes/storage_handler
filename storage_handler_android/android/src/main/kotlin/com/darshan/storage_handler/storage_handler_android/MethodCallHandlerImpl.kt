package com.darshan.storage_handler.storage_handler_android

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.darshan.storage_handler.storage_handler_android.permission.PermissionHandler
import com.darshan.storage_handler.storage_handler_android.permission.PermissionHandler.PermissionSuccessResponse
import com.darshan.storage_handler.storage_handler_android.permission.PermissionResult
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MethodCallHandlerImpl(private val imgHandler: ImageHandler) : MethodChannel.MethodCallHandler {
    companion object {
        const val TAG = "MethodCallHandlerImpl"
    }

    var activity: Activity? = null

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        val permissionHandler = PermissionHandler()
        when (call.method) {
            "saveImage" -> {
                val path: String = call.argument("path")!!
                imgHandler.saveImage(path)
            }
            "requestWritePermission" -> {
                Log.d(TAG, "Request called")
                permissionHandler.requestWritePermission(
                        activity = activity!!,
                        successResponse = object : PermissionSuccessResponse {
                            override fun onSuccess(permissionResult: PermissionResult) {
                                Log.d(TAG, "onSuccess: $permissionResult")
                                result.success(permissionResult.value)
                            }
                        }
                )
            }
            else -> result.notImplemented()
        }
    }
}