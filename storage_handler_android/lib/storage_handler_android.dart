import 'dart:async';

import 'package:flutter/services.dart';

class StorageHandlerAndroid {
  static const MethodChannel _channel =
      const MethodChannel('storage_handler_android');

  Future<String> saveImage(String path) async {
    final String savedPath = await _channel.invokeMethod(
      'saveImage',
      {"path": path},
    );
    return savedPath;
  }

  Future<int> requestWritePermission() async {
    final int result =
        await _channel.invokeMethod<int>("requestWritePermission");
    return result;
  }
}
