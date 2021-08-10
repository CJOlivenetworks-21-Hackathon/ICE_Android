package com.example.ice.activities

import android.media.ImageReader.OnImageAvailableListener
import com.example.ice.env.Logger

class DetectorActivity
    : CameraActivity(),
    OnImageAvailableListener
{
    private val LOGGER: Logger = Logger()

    // Preferences for YOLO
    private val YOLO_MODEL_FILE = "file:///android_asset/graph-tiny-yolo-voc.pb"
    private val YOLO_INPUT_SIZE = 416
    private val YOLO_INPUT_NAME = "input"
    private val YOLO_OUTPUT_NAMES = "output"
    private val YOLO_BLOCK_SIZE = 32


}