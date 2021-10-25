package com.reactnativeotpautofill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpBroadcastReceiver extends BroadcastReceiver {

  private static EditText editText;

  public void setEditText(EditText editText)
  {
    OtpBroadcastReceiver.editText = editText;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

    for (SmsMessage sms : messages)
    {
      String message = sms.getMessageBody();
      Pattern p = Pattern.compile("\\d+");
      Matcher m = p.matcher(message);

      ArrayList<String> codes = new ArrayList<String>();
      while(m.find()) {
        codes.add(m.group());
      }

      if (codes.size() > 0) {
        editText.setText(codes.get(0));
      }
    }
  }
}
