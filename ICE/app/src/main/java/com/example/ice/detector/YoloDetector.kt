package com.example.ice.detector


import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.Trace
import com.example.ice.classifier.Classifier
import com.example.ice.env.SplitTimer
import org.tensorflow.contrib.android.TensorFlowInferenceInterface
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min


/** An object detector that uses TF and a YOLO model to detect objects.  */
class TFYoloDetector private constructor() : Classifier {

    /** Model Reference Settings : from YOLO. */
    // Classes
    private val Classes = arrayOf(
        "cocacola",
        "hottukmix",
        "maratang",
        "SPAM",
        "perrier"
    )
    // Anchors
    private val Anchors = arrayOf(
        0.738768, 0.874946,
        2.42204, 2.65704,
        4.30971, 7.04493,
        10.246, 4.59428,
        12.6868, 11.8741
    )

    // Constants
    private val MAX_RESULTS = 5
    private val NUM_CLASSES = 5
    private val NUM_BOXES_PER_BLOCK = 5

    private var inputName = ""
    private var inputSize = 0

    private var intValues = ArrayList<Int>()
    private var floatValues = ArrayList<Float>()
    private var outputNames = ArrayList<String>()

    private var blockSize = 0

    private var logStats = false

    private var inferenceInterface: TensorFlowInferenceInterface? = null

    /**  Initializes a native TensorFlow session for classifying images  */
    fun create(
        assetManager: AssetManager?,
        modelFilename: String?,
        inputSize: Int,
        inputName: String,
        outputName: String,
        blockSize: Int
    ): Classifier? {
        val d: TFYoloDetector = TFYoloDetector()
        d.inputName = inputName
        d.inputSize = inputSize

        // Pre-allocate buffers.
        d.outputNames = outputName.split(",").toTypedArray().toCollection(ArrayList())
        d.intValues = ArrayList<Int>(inputSize * inputSize)
        d.floatValues = ArrayList<Float>(inputSize * inputSize * 3)
        d.blockSize = blockSize
        d.inferenceInterface = TensorFlowInferenceInterface(assetManager, modelFilename)
        return d
    }

    private fun expit(x: Float): Float { return (1.0 / (1.0 + exp(-x.toDouble()))).toFloat() }

    private fun softmax(values: FloatArray) {
        var maxVal = Float.NEGATIVE_INFINITY
        for (value in values) {
            maxVal = max(maxVal, value)
        }
        var sumVal = 0.0f
        for (i in values.indices) {
            values[i] = exp((values[i] - maxVal).toDouble()).toFloat()
            sumVal += values[i]
        }
        for (i in values.indices) {
            values[i] = values[i] / sumVal
        }
    }

    override fun recognizeImage(bitmap: Bitmap?): List<Classifier.Recognition?>? {
        val timer = SplitTimer("recognizeImage")

        // Log this method so that it can be analyzed with systrace.

        // Log this method so that it can be analyzed with systrace.
        Trace.beginSection("recognizeImage")

        Trace.beginSection("preprocessBitmap")
        // Preprocess the image data from 0-255 int to normalized float based
        // on the provided parameters.
        bitmap!!.getPixels( intValues.toIntArray() , 0, bitmap!!.width, 0, 0, bitmap!!.width, bitmap!!.height)

        for (i in intValues.indices) {
            floatValues[i * 3 + 0] = (intValues[i] shr 16 and 0xFF) / 255.0f
            floatValues[i * 3 + 1] = (intValues[i] shr 8 and 0xFF) / 255.0f
            floatValues[i * 3 + 2] = (intValues[i] and 0xFF) / 255.0f
        }

        // Preprocess Bitmap EndSection
        Trace.endSection()
        // Copy the input data into TensorFlow.
        Trace.beginSection("feed")
        inferenceInterface!!.feed(
            inputName,
            floatValues.toFloatArray(),
            1, inputSize.toLong(), inputSize.toLong(), 3)
        Trace.endSection()

        timer.endSplit("ready for inference")

        // Run the inference call.
        Trace.beginSection("run")
        inferenceInterface!!.run(outputNames.toArray() as Array<out String>?, logStats)
        Trace.endSection()

        timer.endSplit("ran inference")


        // Copy the output Tensor back into the output array.
        Trace.beginSection("fetch")
        val gridWidth = bitmap.width / blockSize
        val gridHeight = bitmap.height / blockSize
        val output =
            FloatArray(gridWidth * gridHeight * (NUM_CLASSES + 5) * NUM_BOXES_PER_BLOCK)
        inferenceInterface!!.fetch(outputNames[0], output)
        Trace.endSection()

        // Find the best detections.

        // Find the best detections.

        // Find the best detections.
        val pq: PriorityQueue<Classifier.Recognition> = PriorityQueue(
            1,
            Comparator<Classifier.Recognition?> { lhs, rhs -> // Intentionally reversed to put high confidence at the head of the queue.
                java.lang.Float.compare(rhs.confidence!!, lhs.confidence!!)
            })

        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                for (b in 0 until NUM_BOXES_PER_BLOCK) {
                    val offset: Int =
                        gridWidth * (NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5)) * y + NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5) * x + (NUM_CLASSES + 5) * b
                    val xPos = (x + expit(output[offset + 0])) * blockSize
                    val yPos = (y + expit(output[offset + 1])) * blockSize
                    val w =
                        (exp(output[offset + 2].toDouble()) * Anchors[2 * b + 0]) as Float * blockSize
                    val h =
                        (exp(output[offset + 3].toDouble()) * Anchors[2 * b + 1]) as Float * blockSize
                    val rect = RectF(
                        max(0f, xPos - w / 2),
                        max(0f, yPos - h / 2),
                        min((bitmap.width - 1).toFloat(), xPos + w / 2),
                        min((bitmap.height - 1).toFloat(), yPos + h / 2)
                    )
                    val confidence = expit(output[offset + 4])
                    var detectedClass = -1
                    var maxClass = 0f
                    val classes = FloatArray(NUM_CLASSES)
                    for (c in 0 until NUM_CLASSES) {
                        classes[c] = output[offset + 5 + c]
                    }
                    softmax(classes)
                    for (c in 0 until NUM_CLASSES) {
                        if (classes[c] > maxClass) {
                            detectedClass = c
                            maxClass = classes[c]
                        }
                    }
                    val confidenceInClass = maxClass * confidence
                    if (confidenceInClass > 0.01) {
                        pq.add(
                            Classifier.Recognition(
                                "" + offset,
                                Classes[detectedClass],
                                confidenceInClass,
                                rect
                            )
                        )
                    }
                }
            }
        }

        timer.endSplit("decoded results")

        val recognitions: ArrayList<Classifier.Recognition> = ArrayList()
        for (i in 0 until min(
            pq.size,
            MAX_RESULTS
        )) {
            recognitions.add(pq.poll())
        }
        Trace.endSection() // "recognizeImage"

        timer.endSplit("processed results")

        return recognitions
    }

    override fun enableStatLogging(logStats: Boolean) { this.logStats = logStats }

    override fun getStatString(): String { return inferenceInterface!!.statString }

    override fun close() { inferenceInterface!!.close() }
}