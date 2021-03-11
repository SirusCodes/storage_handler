package com.darshan.storage_handler.storage_handler_android

import android.content.ContentResolver
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel

/** StorageHandlerAndroidPlugin */
class StorageHandlerAndroidPlugin : FlutterPlugin, ActivityAware {
    private var channel: MethodChannel? = null
    private lateinit var resolver: ContentResolver
    private lateinit var imageHandler: ImageHandler
    private var methodHandler: MethodCallHandlerImpl? = null

    @Suppress("DEPRECATION")
    fun registerWith(@NonNull pluginRegistrar: io.flutter.plugin.common.PluginRegistry.Registrar) {
        channel = MethodChannel(pluginRegistrar.messenger(), "storage_handler_android")

        resolver = pluginRegistrar.activeContext().contentResolver
        imageHandler = ImageHandler(resolver)

        methodHandler = MethodCallHandlerImpl(imageHandler)

        channel!!.setMethodCallHandler(methodHandler)
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "storage_handler_android")
        resolver = flutterPluginBinding.applicationContext.contentResolver
        imageHandler = ImageHandler(resolver)

        methodHandler = MethodCallHandlerImpl(imageHandler)

        channel!!.setMethodCallHandler(methodHandler)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel!!.setMethodCallHandler(null)
        channel = null
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        if (methodHandler != null) {
            methodHandler!!.activity = binding.activity
        }
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        if (methodHandler != null) {
            methodHandler!!.activity = null
        }
    }
}
