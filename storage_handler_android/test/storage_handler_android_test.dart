// import 'package:flutter/services.dart';
// import 'package:flutter_test/flutter_test.dart';
// import 'package:storage_handler_android/storage_handler_android.dart';

// void main() {
//   const MethodChannel channel = MethodChannel('storage_handler_android');

//   TestWidgetsFlutterBinding.ensureInitialized();

//   setUp(() {
//     channel.setMockMethodCallHandler((MethodCall methodCall) async {
//       return '42';
//     });
//   });

//   tearDown(() {
//     channel.setMockMethodCallHandler(null);
//   });

//   test('getPlatformVersion', () async {
//     expect(await StorageHandlerAndroid.platformVersion, '42');
//   });
// }
