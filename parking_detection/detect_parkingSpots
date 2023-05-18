import cv2
import numpy as np
from copy import copy,deepcopy

def dilate(img):
    kernel_size=np.ones((3,3),np.uint8)
    
    gray=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    blur=cv2.GaussianBlur(gray, (3,3), 1)
    Thresholded=cv2.adaptiveThreshold(blur,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY_INV,25,16)
    blur=cv2.medianBlur(Thresholded, 5)
    dilate=cv2.dilate(blur,kernel_size,iterations=1)
    return dilate


def get_parkingSpot(img):
    free_spots=0
    a=[]
    b=[]
    img_part1=copy(img[90:670,50:270])
    h,w=img_part1.shape[:2]
    nb=int(h/12)
    for i in range(0,12):
        img_part1_=copy(img_part1[i*nb:(i+1)*nb,0:int(w/2)])
        img_part2_=copy(img_part1[i*nb:(i+1)*nb,int(w/2):w])
        
        
        
        pixels_nb_part1=cv2.countNonZero(dilate(img_part1_))
        pixels_nb_part2=cv2.countNonZero(dilate(img_part2_))
        if(i==10):
            a.append(pixels_nb_part1)
            b.append(pixels_nb_part2)
            if(pixels_nb_part1>=745):
                cv2.rectangle(img_part1,(0,i*nb),(int(w/2),(i+1)*nb),(0,0,255),2)
            elif(pixels_nb_part1<745):
                free_spots+=1
                cv2.rectangle(img_part1,(0,i*nb),(int(w/2),(i+1)*nb),(0,255,0),2)
        if(pixels_nb_part1>=900):
            cv2.rectangle(img_part1,(0,i*nb),(int(w/2),(i+1)*nb),(0,0,255),2)
        elif(pixels_nb_part1<900):
            free_spots+=1
            cv2.rectangle(img_part1,(0,i*nb),(int(w/2),(i+1)*nb),(0,255,0),2)
        if(pixels_nb_part2>=700):
            cv2.rectangle(img_part1,(int(w/2),i*nb),(w,(i+1)*nb),(0,0,255),2)
        elif(pixels_nb_part2<700):
            free_spots+=1
            cv2.rectangle(img_part1,(int(w/2),i*nb),(w,(i+1)*nb),(0,255,),2)

    
    img_part2=copy(img[90:665,400:620])
    
    h,w=img_part2.shape[:2]
    nb=int(h/12)
    for i in range(0,12):
        img_part1_=copy(img_part2[i*nb:(i+1)*nb,0:int(w/2)])
        img_part2_=copy(img_part2[i*nb:(i+1)*nb,int(w/2):w])

        if(i!=8):
            pixels_nb_part1=cv2.countNonZero(dilate(img_part1_))
            pixels_nb_part2=cv2.countNonZero(dilate(img_part2_))
            if(pixels_nb_part1>1000):
                cv2.rectangle(img_part2,(0,i*nb+5),(int(w/2),(i+1)*nb+5),(0,0,255),2)
            elif(pixels_nb_part1<1000):
                free_spots+=1
                cv2.rectangle(img_part2,(0,i*nb+5),(int(w/2),(i+1)*nb+5),(0,255,0),2)
            if(pixels_nb_part2>1000):
                cv2.rectangle(img_part2,(int(w/2),i*nb+5),(w,(i+1)*nb+5),(0,0,255),2)
            elif(pixels_nb_part2<1000):
                free_spots+=1
                cv2.rectangle(img_part2,(int(w/2),i*nb+5),(w,(i+1)*nb+5),(0,255,0),2)
        
    img_part3=copy(img[80:665,750:1010])
    h,w=img_part3.shape[:2]
    nb=int(h/12)
    for i in range(0,12):
        img_part1_=copy(img_part3[i*nb:(i+1)*nb,0:int(w/2)])
        img_part2_=copy(img_part3[i*nb:(i+1)*nb,int(w/2):w])
        pixels_nb_part1=cv2.countNonZero(dilate(img_part1_))
        pixels_nb_part2=cv2.countNonZero(dilate(img_part2_))
        if(pixels_nb_part1>1000):
            cv2.rectangle(img_part3,(0,i*nb+5),(int(w/2)-20,(i+1)*nb+5),(0,0,255),2)
        elif(pixels_nb_part1<1000):
            free_spots+=1
            cv2.rectangle(img_part3,(0,i*nb+5),(int(w/2)-20,(i+1)*nb+5),(0,255,0),2)
        if(i!=0):
            if(pixels_nb_part2>1000):
                cv2.rectangle(img_part3,(int(w/2)+25,i*nb+5),(w,(i+1)*nb+5),(0,0,255),2)
            elif(pixels_nb_part2<1000):
                free_spots+=1
                cv2.rectangle(img_part3,(int(w/2)+25,i*nb+5),(w,(i+1)*nb+5),(0,255,0),2)
    
    img[90:670,50:270]=img_part1
    img[90:665,400:620]=img_part2
    img[80:665,750:1010]=img_part3
    
    return img,free_spots,a,b
img=cv2.imread('parking.png')
video=cv2.VideoCapture('carPark.mp4')
while(1):
    ret,img=video.read()
    if not ret:
        break
    result,free,a,b=get_parkingSpot(img)
    cv2.putText(result, str(free)+' free spots',(0,25),cv2.FONT_HERSHEY_SIMPLEX,1,(0,255,0), 2)
    cv2.imshow('test',result)
    if(cv2.waitKey(30)==ord('q')):
        cv2.destroyAllWindows()
        break
print(min(a),min(b))
cv2.destroyAllWindows()
