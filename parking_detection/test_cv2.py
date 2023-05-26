import cv2

def test_cv2():
    image = cv2.imread('licence.jpg')

    if image is not None:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        blurred = cv2.GaussianBlur(gray, (5, 5), 0)
        edges = cv2.Canny(blurred, 50, 150)
        
        if edges is not None:
            print("CV2 test passed!")
            return 1
        else:
            print("CV2 test failed")
            return 0
    else:
        print("CV2 test failed: Unable to load image")
        return 0
test_cv2()
