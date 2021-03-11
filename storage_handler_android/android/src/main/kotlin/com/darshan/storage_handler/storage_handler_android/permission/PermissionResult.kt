package com.darshan.storage_handler.storage_handler_android.permission

enum class PermissionResult(val value: Int) {
    GRANTED(1),
    DENIED(0),
    DENIED_FOREVER(2)
}