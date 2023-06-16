package com.capstone.tastematch.presentation.add

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.capstone.tastematch.R
import com.capstone.tastematch.databinding.ActivityAddDetectionMenuBinding
import com.capstone.tastematch.ml.IngredientDetection
import com.capstone.tastematch.presentation.main.MainActivity
import com.capstone.tastematch.presentation.result.ResultActivity
import com.capstone.tastematch.presentation.search.SearchFragment
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class AddDetectionMenuActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var bitmap: Bitmap
    private lateinit var buClassify: Button
    private lateinit var change:Button
    private lateinit var binding :ActivityAddDetectionMenuBinding
    private var filteredIngredients: List<Pair<String, Float>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddDetectionMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (::bitmap.isInitialized && bitmap != null) {
        }else{
            showPop()
        }

        imageView = binding.imageview
        buClassify = binding.classify
        change = binding.change

        change.setOnClickListener {
            showPop()
        }


        buClassify.setOnClickListener {
            if (!::bitmap.isInitialized || bitmap == null) {
                showToast("Please capture or upload an image first")
                return@setOnClickListener
            }

            val model = IngredientDetection.newInstance(this)
            val categoryIndex = getCategoryIndex()
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, INPUT_SIZE, INPUT_SIZE, PIXEL_SIZE), DataType.FLOAT32)

            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(resizedBitmap)
            inputFeature0.loadBuffer(tensorImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            val detectionResults = mutableListOf<Pair<String, Float>>()
            val lastIndex = minOf(outputFeature0.size, categoryIndex.size)
            for (i in 0 until lastIndex) {
                detectionResults.add(Pair(categoryIndex[i], outputFeature0[i]))
            }
            detectionResults.sortByDescending { it.second }
            val top10Results = detectionResults.take(10)

            // Assuming `top10Results` contains the top 10 classes and scores
            val threshold = 0.3f // Adjust the threshold as needed
            val filteredResults = top10Results.filter { it.second > threshold }

            val finalResult = filteredResults.maxByOrNull { it.second }
            finalResult?.let {
                val ingredientClass = it.first

                // Create an Intent to start the ResultActivity
                val intent = Intent(this, ResultActivity::class.java)
                // Pass the ingredient class as an extra in the Intent
                intent.putExtra("ingredientClass", ingredientClass)
                Log.d("Result : ",ingredientClass)
                Toast.makeText(this, "Successful Detection: $ingredientClass", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            model.close()
        }

    }
    @Suppress("DEPRECATION")
    private fun showPop() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheesh)

        val galleryButton: Button = dialog.findViewById(R.id.gallery)
        val cameraButton: Button = dialog.findViewById(R.id.Camera)

        galleryButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_IMAGE_SELECT
            )
            dialog.dismiss()
        }

        cameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                val defaultCamera = getDefaultBackCamera()
                if (defaultCamera != null) {
                    intent.putExtra("android.intent.extras.CAMERA_FACING", defaultCamera)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                } else {
                    showToast("Failed to open camera")
                }
            } else {
                showToast("Camera app not found")
            }
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    @Suppress("DEPRECATION")
    private fun getDefaultBackCamera(): Int? {
        val cameraInfo = Camera.CameraInfo()
        val cameraCount = Camera.getNumberOfCameras()
        for (cameraId in 0 until cameraCount) {
            Camera.getCameraInfo(cameraId, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return cameraId
            }
        }
        return null
    }
    private fun convertToARGB8888(bitmap: Bitmap): Bitmap {
        return bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }
    private fun getCategoryIndex(): List<String> {
        val categoryList = mutableListOf<String>()

        try {
            // Open the file from the assets folder
            val inputStream: InputStream = assets.open("label.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Read each line and add it to the category list
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                categoryList.add(line.orEmpty())
            }

            // Close the reader
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return categoryList
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                if (bitmap != null) {
                    Glide.with(this)
                        .load(bitmap)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(imageView)
                } else {
                    showToast("Failed to load image")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            val extras = data.extras
            val capturedImage = extras?.get("data") as Bitmap
            if (capturedImage != null) {
                val argbBitmap = convertToARGB8888(capturedImage) // Convert bitmap to ARGB_8888 configuration
                bitmap = argbBitmap
                Glide.with(this)
                    .load(bitmap)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(imageView)
            } else {
                showToast("Failed to capture image")
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 13
        private const val REQUEST_IMAGE_SELECT = 12
        private const val INPUT_SIZE = 320
        private const val PIXEL_SIZE = 3
    }

}
