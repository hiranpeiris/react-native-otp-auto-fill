package com.reactnativeotpautofill;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class OtpView extends LinearLayout implements LifecycleEventListener {
  private static final String TAG = OtpView.class.getSimpleName();
  private ThemedReactContext themedContext;
  private OtpBroadcastReceiver otpReceiver;
  private EditText numberText;
  private int length;
  private boolean isReceiverRegistered = false;

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

  void receiveMessage(String message) {
    if (themedContext == null) {
      return;
    }

    if (!themedContext.hasActiveCatalystInstance()) {
      return;
    }

    numberText.setText(message);
  }

  private void setup() {
    inflate(themedContext, R.layout.otp_view_layout, this);
    numberText = (EditText) findViewById(R.id.numberText);

    setOtpTextColor("#000000");
    setOtpTextSpace(1);
    setOtpTextFontSize(24);
    setOtpTextLength(6);

    numberText.addTextChangedListener(new TextWatcher() {

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= length) {
          WritableMap args = Arguments.createMap();
          args.putString("code", s.toString());

          themedContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "onComplete", args);
        }
      }

      @Override
      public void afterTextChanged(Editable s) { }
    });

    otpReceiver = new OtpBroadcastReceiver(this);

    SmsRetrieverClient client = SmsRetriever.getClient(themedContext);
    Task<Void> task = client.startSmsRetriever();
    task.addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        Log.e(TAG, "started sms listener");
      }
    });

    task.addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Could not start sms listener", e);
      }
    });
  }

  private void registerReceiverIfNecessary(BroadcastReceiver receiver) {
    if (themedContext.getCurrentActivity() == null) return;
    try {
      themedContext.getCurrentActivity().registerReceiver(
        receiver,
        new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
      );
      Log.d(TAG, "Receiver Registered");
      isReceiverRegistered = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void unregisterReceiver(BroadcastReceiver receiver) {
    if (isReceiverRegistered && themedContext.getCurrentActivity() != null && receiver != null) {
      try {
        themedContext.getCurrentActivity().unregisterReceiver(receiver);
        Log.d(TAG, "Receiver UnRegistered");
        isReceiverRegistered = false;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onHostResume() {
    registerReceiverIfNecessary(otpReceiver);
  }

  @Override
  public void onHostPause() {
    unregisterReceiver(otpReceiver);
  }

  @Override
  public void onHostDestroy() {
    unregisterReceiver(otpReceiver);
  }
}