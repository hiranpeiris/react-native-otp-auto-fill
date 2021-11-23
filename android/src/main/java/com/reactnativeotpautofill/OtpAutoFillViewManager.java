package com.reactnativeotpautofill;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class OtpAutoFillViewManager extends SimpleViewManager<View> {
    public static final String REACT_CLASS = "OtpAutoFillView";

    @Override
    @NonNull
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    @NonNull
    public View createViewInstance(ThemedReactContext reactContext) {
        return new OtpView(reactContext);
    }

    @ReactProp(name = "color")
    public void setColor(OtpView view, String color) {
      view.setOtpTextColor(color);
    }

    @ReactProp(name = "space")
    public void setSpace(OtpView view, float space) {
      view.setOtpTextSpace(space);
    }

    @ReactProp(name = "fontSize")
    public void setFontSize(OtpView view, float fontSize) {
      view.setOtpTextFontSize(fontSize);
    }

    @ReactProp(name = "length")
    public void setTextLength(OtpView view, int length) {
      view.setOtpTextLength(length);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
      Map<String, Object> export = super.getExportedCustomDirectEventTypeConstants();

      if (export == null) {
        export = MapBuilder.newHashMap();
      }

      export.put("complete", MapBuilder.of("registrationName", "onComplete"));
      export.put("androidSignature", MapBuilder.of("registrationName", "onAndroidSignature"));

      return export;
    }
}
