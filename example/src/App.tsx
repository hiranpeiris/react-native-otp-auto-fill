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
        length={4} // Define the length od OTP. This is a must
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
