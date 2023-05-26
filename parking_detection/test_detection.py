import unittest
import cv2
from ultralytics import YOLO
class TestYOLO(unittest.TestCase):
    def test_detect_object(self):
        yolo_object = YOLO()
        image = cv2.imread("test_image.jpg")
        objects = yolo_object.detect_objects(image)
        self.assertTrue(len(objects) > 0)
