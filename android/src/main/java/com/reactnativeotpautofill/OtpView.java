package com.reactnativeotpautofill;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;

public class OtpView extends LinearLayout {
  private String TAG = "com.reactnativeotpautofill";
  private ThemedReactContext themedContext;
  private EditText numberText;
  private int length;

  public OtpView(ThemedReactContext context) {
    super(context);
    this.themedContext = context;
    setup();
  }

  public void setOtpTextColor(String color) {
    numberText.setTextColor(Color.parseColor(color));
  }

  public void setOtpTextSpace(float space) {
    numberText.setLetterSpacing(space);
  }

  public void setOtpTextFontSize(float size) {
    numberText.setTextSize(size);
  }

  public void setOtpTextLength(int length) {
    numberText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
    this.length = length;
  }

  private void setup() {
    inflate(themedContext, R.layout.otp_view_layout, this);
    numberText = (EditText) findViewById(R.id.numberText);

    setOtpTextColor("#000000");
    setOtpTextSpace(1);
    setOtpTextFontSize(24);
    setOtpTextLength(4);

    numberText.addTextChangedListener(new TextWatcher() {

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= length) {
          WritableMap args = Arguments.createMap();
          args.putString("code", s.toString());
          themedContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "complete", args);
        }
      }

      @Override
      public void afterTextChanged(Editable s) { }
    });

    startSMSRetrieverClient();
    new OtpBroadcastReceiver().setEditText(numberText);
  }

  private void startSMSRetrieverClient() {
    SmsRetrieverClient client = SmsRetriever.getClient(themedContext);
    Task<Void> task = client.startSmsRetriever();
    task.addOnSuccessListener(aVoid -> {
      Log.d(TAG, "start retriever");
      AppSignatureHelper appSignatureHelper = new AppSignatureHelper(themedContext);
      WritableMap args = Arguments.createMap();
      args.putString("code", appSignatureHelper.getAppSignatures().get(0));
      themedContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "androidSignature", args);
    });
    task.addOnFailureListener(e -> {
      Log.d(TAG, "unable to start retriever");
    });
  }
}
