package com.appleeducate.fluttersms;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/** FlutterSmsPlugin */
public class FlutterSmsPlugin implements MethodCallHandler {
  Activity activity;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_sms");
    channel.setMethodCallHandler(new FlutterSmsPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("sendSMS")) {
      String message = call.argument("message");
      ArrayList<String> recipients = call.argument("recipients");
      sendSMS(recipients, message);
      result.success("Sent!");
    } else {
      result.notImplemented();
    }
  }

  private FlutterSmsPlugin(Activity activity) {
    this.activity = activity;
  }

  private void sendSMS(ArrayList<String> phones, String message) {
    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
    sendIntent.putExtra("sms_body", message);
    sendIntent.setData(Uri.parse("smsto:" + phones));
    activity.startActivity(sendIntent);
  }
}
