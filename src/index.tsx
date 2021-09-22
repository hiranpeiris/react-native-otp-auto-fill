import { requireNativeComponent, ViewStyle } from 'react-native';

type OtpAutoFillProps = {
  color: string;
  style: ViewStyle;
};

export const OtpAutoFillViewManager = requireNativeComponent<OtpAutoFillProps>(
'OtpAutoFillView'
);

export default OtpAutoFillViewManager;
