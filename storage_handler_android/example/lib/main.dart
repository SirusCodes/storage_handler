import 'package:flutter/material.dart';
import 'package:storage_handler_android/storage_handler_android.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  StorageHandlerAndroid handlerAndroid = StorageHandlerAndroid();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Test Screen'),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () async {
            final res = await handlerAndroid.requestWritePermission();

            print(res);
          },
        ),
      ),
    );
  }
}
