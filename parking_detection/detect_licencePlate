import cv2
from ultralytics import YOLO
from copy import copy
import torch
import pytesseract
import os

pytesseract.pytesseract.tesseract_cmd=r'tesseract.exe'
tessdata_dir_config = r'--tessdata-dir "tessdata"'
os.environ['TESSDATA_PREFIX'] = 'tessdata'


lang = 'ara'
config = '--psm 6 --oem 3 -c tessedit_char_whitelist=أبتثجحخدذرزسشصضطظعغفقكلمنهوي0123456789'

model=YOLO('yolov8x.pt')
model.to('cuda')

torch.cuda.empty_cache()
img=cv2.imread('licence.jpg')
cv2.imshow('test',img)
if(cv2.waitKey(0)==ord('q')):
    cv2.destroyAllWindows()
img_copy=copy(img)
result=model.predict(img)
for r in result:
    boxes=r.boxes
    for box in boxes:
        b=box.xyxy[0]
        x1,y1,x2,y2=int(b[0]),int(b[1]),int(b[2]),int(b[3])
        cv2.rectangle(img,(x1,y1),(x2,y2),(0,255,0),2)
        h,w=y2-y1,x2-x1
        if(h>500):
            cropped_img=img_copy[y1:y1+h,x1:x1+w]
            gray=cv2.cvtColor(cropped_img,cv2.COLOR_BGR2GRAY)
            blur=cv2.GaussianBlur(gray,(5,5),0)
            edges=cv2.Canny(blur,100,200)
            contours,hierarchy=cv2.findContours(edges,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
            for contour in contours:
                x,y,weight,height=cv2.boundingRect(contour)
                aspect_ratio=w/h
                if(weight > 100 and weight < 300 and height > 30 and height < 60  ):
                    cv2.rectangle(cropped_img,(x,y),(x+weight,y+height),(0,255,0),2)
                    ocr_img=copy(cropped_img[y:y+height,x:x+weight])
                    gray_detected=cv2.cvtColor(ocr_img,cv2.COLOR_BGR2GRAY)
                    thresh = cv2.threshold(gray_detected, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)[1]

 
                    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3,3))
                    opening = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel, iterations=1)
                    opening = cv2.GaussianBlur(opening, (1,1), 0)

                    text = pytesseract.image_to_string(opening,lang=lang)
                    
                    print(text)
cv2.imshow('test',opening)
if(cv2.waitKey(0)==ord('q')):
    cv2.destroyAllWindows()
    
