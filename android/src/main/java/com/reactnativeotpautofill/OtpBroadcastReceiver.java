package com.reactnativeotpautofill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpBroadcastReceiver extends BroadcastReceiver {

  private static EditText editText;

  public void setEditText(EditText editText) {
    OtpBroadcastReceiver.editText = editText;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
      Bundle extras = intent.getExtras();
      if (extras != null) {
        String message = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE);
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(message);

        ArrayList<String> codes = new ArrayList<String>();
        while (m.find()) {
          codes.add(m.group());
        }

        if (codes.size() > 0) {
          editText.setText(codes.get(0));
        }
      }
    }
  }
}
