import { requireNativeComponent, ViewStyle } from 'react-native';

type OtpAutoFillProps = {
  color: string;
  space: number;
  fontSize: number;
  length: number;
  style: ViewStyle;
};

export const OtpAutoFillViewManager =
  requireNativeComponent<OtpAutoFillProps>('OtpAutoFillView');

export default OtpAutoFillViewManager;
