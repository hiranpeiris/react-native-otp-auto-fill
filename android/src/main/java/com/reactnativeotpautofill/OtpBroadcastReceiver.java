package com.reactnativeotpautofill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class OtpBroadcastReceiver extends BroadcastReceiver {

  private OtpView otpView;

  public OtpBroadcastReceiver(OtpView otpView) {
    this.otpView = otpView;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    String o = intent.getAction();
    if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(o)) {
      Bundle extras = intent.getExtras();
      Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

      if (status == null) {
        return;
      }

      switch (status.getStatusCode()) {
        case CommonStatusCodes.SUCCESS:
          // Get SMS message contents
          String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
          otpView.receiveMessage(message);
          if (message != null) {
            Log.d("SMS", message);
          }
          break;
        case CommonStatusCodes.TIMEOUT:
          Log.d("SMS", "Timeout error");
          break;
      }
    }
  }
}
