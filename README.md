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

  return (
    <View style={styles.container}>
      <OtpAutoFillViewManager onComplete={handleComplete} style={styles.box} />
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

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
