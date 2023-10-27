package com.example.brorentalapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brorentalapp.databinding.ActivityUploadBikesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.*

class UploadBikes : AppCompatActivity() {
    lateinit var binding: ActivityUploadBikesBinding
    private val CAMERA_REQUEST_CODE = 101
    private val GALLERY_REQUEST_CODE = 102
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.getReference("Your Ride")
    private var url: String = ""
    private var check: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBikesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadImage.setOnClickListener {
            showImagePickerDialog()
        }
        binding.uploadBtn.setOnClickListener {
            binding.uploadProgress.visibility = View.VISIBLE
            val category = binding.categoryText.text.toString()
            val name = binding.nameText.text.toString()
            val price = binding.priceText.text.toString()

            if (url.isEmpty() || category.isEmpty() || name.isEmpty() || price.isEmpty()) {
                binding.uploadProgress.visibility = View.GONE
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                val database = FirebaseDatabase.getInstance()
                val reference: DatabaseReference = database.getReference("Your Ride")

                val rideData = RideData(url, category, name, price)

                reference.child(name).setValue(rideData)
                binding.uploadImage.setImageResource(R.drawable.cycle_3)
                binding.categoryText.setText("")
                binding.nameText.setText("")
                binding.priceText.setText("")
                binding.uploadProgress.visibility = View.GONE
            }
        }
        binding.bckBtn.setOnClickListener {
            onBackPressed()
        }
        binding.RcImage.setOnClickListener {
            check = true
            showImagePickerDialog()

        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Image Source")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> takePhotoFromCamera()
                1 -> choosePhotoFromGallery()
                2 -> builder.create().dismiss()
            }
        }
        builder.show()
    }

    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    binding.uploadProgress.visibility = View.VISIBLE
                    // Handle the camera image capture result
                    // The captured image is available in the 'data' parameter of the callback
                    val imageBitmap = data?.extras?.get("data") as Bitmap

                    // Convert the imageBitmap to a ByteArray
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    val imageByteArray = byteArrayOutputStream.toByteArray()

                    // Create a reference to the location in Firebase Storage where the image will be uploaded
                    val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

                    // Upload the image to Firebase Storage
                    imageRef.putBytes(imageByteArray)
                        .addOnSuccessListener { taskSnapshot ->
                            // Image uploaded successfully
                            Toast.makeText(
                                this,
                                "Camera Image Uploaded Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.uploadProgress.visibility = View.GONE
                            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                                // Uri of the downloaded image
                                url = uri.toString()
                                if (check) {
                                    Picasso.get().load(url).into(binding.RcImage)
                                } else {
                                    // Load and display the image using Picasso
                                    Picasso.get().load(url).into(binding.uploadImage)
                                }
                            }
                            // You can use downloadUrl to access the uploaded image

                        }
                        .addOnFailureListener { exception ->
                            // Handle the error if the image upload fails
                            binding.uploadProgress.visibility = View.GONE
                            Toast.makeText(this, "Error Coming", Toast.LENGTH_SHORT).show()
                        }

                }
                GALLERY_REQUEST_CODE -> {
                    // Handle the gallery image selection result
                    // The selected image is available in the 'data' parameter of the callback
                    binding.uploadProgress.visibility = View.VISIBLE
                    val selectedImageUri = data?.data

                    if (selectedImageUri != null) {
                        // Create a reference to the location in Firebase Storage where the image will be uploaded
                        val imageRef =
                            storageRef.child("images/${selectedImageUri.lastPathSegment}")

                        // Upload the image to Firebase Storage
                        imageRef.putFile(selectedImageUri)
                            .addOnSuccessListener { taskSnapshot ->
                                // Image uploaded successfully
                                binding.uploadProgress.visibility = View.GONE
                                Toast.makeText(
                                    this,
                                    "Gallery Image Uploaded Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                                    // Uri of the downloaded image
                                    url = uri.toString()
                                    if (check) {
                                        Picasso.get().load(url).into(binding.RcImage)
                                    } else {
                                        // Load and display the image using Picasso
                                        Picasso.get().load(url).into(binding.uploadImage)
                                    }
                                }
                                // You can use downloadUrl to access the uploaded image


                            }
                            .addOnFailureListener { exception ->
                                // Handle the error if the image upload fails
                                binding.uploadProgress.visibility = View.GONE
                                Toast.makeText(this, "Error Coming", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }
    }
}
