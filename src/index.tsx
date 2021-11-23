import {
  requireNativeComponent,
  ViewStyle,
  NativeSyntheticEvent,
} from 'react-native';

type OtpAutoFillProps = {
  color?: string;
  space?: number;
  fontSize?: number;
  length?: number;
  style?: ViewStyle;
  onComplete: (event: NativeSyntheticEvent<{ code: string }>) => void;
  onAndroidSignature?: (event: NativeSyntheticEvent<{ code: string }>) => void;
};

export const OtpAutoFillViewManager =
  requireNativeComponent<OtpAutoFillProps>('OtpAutoFillView');

export default OtpAutoFillViewManager;
