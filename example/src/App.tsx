import * as React from 'react';

import { StyleSheet, View } from 'react-native';
import OtpAutoFillViewManager from 'react-native-otp-auto-fill';

export default function App() {
  return (
    <View style={styles.container}>
      <OtpAutoFillViewManager style={styles.box} />
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
