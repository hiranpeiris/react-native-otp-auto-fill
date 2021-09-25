#import "React/RCTViewManager.h"

@interface RCT_EXTERN_MODULE(OtpAutoFillViewManager, RCTViewManager)

RCT_EXPORT_VIEW_PROPERTY(color, NSString)
RCT_EXPORT_VIEW_PROPERTY(space, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(fontSize, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(length, NSNumber)

RCT_EXPORT_VIEW_PROPERTY(onComplete, RCTDirectEventBlock)

@end
