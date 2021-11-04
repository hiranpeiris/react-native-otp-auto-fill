package com.reactnativeotpautofill;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class OtpView extends LinearLayout {
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

    //TODO: Android implementation
    //requestSMSPermission();
    new OtpBroadcastReceiver().setEditText(numberText);
  }

  private void requestSMSPermission()
  {
    String permission = Manifest.permission.RECEIVE_SMS;

    int grant = ContextCompat.checkSelfPermission(getContext(), permission);
    if (grant != PackageManager.PERMISSION_GRANTED)
    {
      String[] permission_list = new String[1];
      permission_list[0] = permission;
      ActivityCompat.requestPermissions(themedContext.getCurrentActivity(), permission_list,1);
    }
  }
}
