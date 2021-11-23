# react-native-otp-auto-fill

Auto fill OTP library for React Native.
User don't have to enter the OTP Code manually... :)
This library reads the OTP Code from SMS and fill it Automatically in the Text Field.

Open for more contributors...

Support Only: 
- iOS 12.0 or above. ( iOS Pods > platform :ios, '12.0' )
- Android 5.0 (API Level 21) or above. ( Android Gradle > minSdkVersion = 21 )

## Installation

```sh
npm install react-native-otp-auto-fill
```

## Usage

```js
import * as React from 'react';
import { Alert, NativeSyntheticEvent, StyleSheet, View } from 'react-native';
import OtpAutoFillViewManager from 'react-native-otp-auto-fill';

export default function App() {
  const handleComplete = ({
    nativeEvent: { code },
  }: NativeSyntheticEvent<{ code: string }>) => {
    Alert.alert('OTP Code Received!', code);
  };

  // This is only needed once to get the Android Signature key for SMS body
  const handleOnAndroidSignature = ({
    nativeEvent: { code },
  }: NativeSyntheticEvent<{ code: string }>) => {
    console.log('Android Signature Key for SMS body:', code);
  };

  return (
    <View style={styles.container}>
      <OtpAutoFillViewManager
        onComplete={handleComplete}
        onAndroidSignature={handleOnAndroidSignature}
        style={styles.box}
        length={4} // Define the length of OTP code. This is a must.
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 300,
    height: 55,
    marginVertical: 20,
    borderColor: 'red',
    borderWidth: 1,
  },
});
```

Check your console logs:
You will get "Android Signature key for SMS body: 4H8FDxP/hbp"
Put that code "4H8FDxP/hbp" in backend SMS body like below.

## Example SMS body:

Your ExampleApp code is: 8468

4H8FDxP/hbp

## Note
Define the length of OTP code. This is a must.

eg:

```js
<OtpAutoFillViewManager
  onComplete={handleComplete}
  onAndroidSignature={handleOnAndroidSignature}
  style={styles.box}
  length={4} // Define the length of OTP code. This is a must.
/>
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
