@objc(OtpAutoFillViewManager)
class OtpAutoFillViewManager: RCTViewManager {

    override func view() -> (OtpAutoFillView) {
        return OtpAutoFillView()
    }
}

class OtpAutoFillView : UIView, UITextFieldDelegate {
    
    var textField = UITextField()

    @objc var color: String = "#000000" {
        didSet {
            textField.textColor = hexStringToUIColor(hexColor: color)
        }
    }

    @objc var space: NSNumber = 28.0 {
        didSet {
            textField.defaultTextAttributes.updateValue(space, forKey: NSAttributedString.Key.kern)
        }
    }

    @objc var fontSize: NSNumber = 24.0 {
        didSet {
            textField.font = UIFont.systemFont(ofSize: CGFloat(fontSize), weight: .heavy)
        }
    }

    @objc var length: NSNumber = 6 {
        didSet {
            textField.text = ""
        }
    }

    override func draw(_ rect: CGRect) {
        textField = UITextField(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height))
        textField.textColor = hexStringToUIColor(hexColor: color)
        textField.defaultTextAttributes.updateValue(space, forKey: NSAttributedString.Key.kern)
        textField.font = UIFont.systemFont(ofSize: CGFloat(fontSize), weight: .heavy)
        textField.textContentType = .oneTimeCode
        textField.keyboardType = .numberPad
        textField.textAlignment = .center

        self.addSubview(textField)
        textField.delegate = self
        textField.becomeFirstResponder()
    }

    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let currentString: NSString = (textField.text ?? "") as NSString
        let newString: NSString =
            currentString.replacingCharacters(in: range, with: string) as NSString
        
        if (newString.length == length.intValue) {
            print("Number: \(newString)");
        }
        
        return newString.length <= length.intValue
    }

    func hexStringToUIColor(hexColor: String) -> UIColor {
        let stringScanner = Scanner(string: hexColor)

        if(hexColor.hasPrefix("#")) {
            stringScanner.scanLocation = 1
        }
        var color: UInt32 = 0
        stringScanner.scanHexInt32(&color)

        let r = CGFloat(Int(color >> 16) & 0x000000FF)
        let g = CGFloat(Int(color >> 8) & 0x000000FF)
        let b = CGFloat(Int(color) & 0x000000FF)

        return UIColor(red: r / 255.0, green: g / 255.0, blue: b / 255.0, alpha: 1)
    }
}
