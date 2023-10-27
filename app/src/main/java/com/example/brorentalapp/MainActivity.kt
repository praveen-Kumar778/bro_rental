package com.example.brorentalapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.brorentalapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
 /*   private val storage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference

    private lateinit var aadhaarButton: Button
    private lateinit var panButton: Button
    private lateinit var profileButton: Button

    private val PICK_IMAGE_REQUEST = 4
    private var selectedImageUri: Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST_AADHAAR = 1
        const val PICK_IMAGE_REQUEST_PAN = 2
        const val PICK_IMAGE_REQUEST_PROFILE = 3
        const val CAMERA_REQUEST = 100
        const val GALLERY_REQUEST = 101
    }
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        FirebaseApp.initializeApp(this)
        setContentView(binding.root)
       /* aadhaarButton = findViewById(R.id.adhaar_btn)
        panButton = findViewById(R.id.pan_btn)
        profileButton = findViewById(R.id.takeSelfie)

        binding.adhaarBtn.setOnClickListener {
            pickImage(PICK_IMAGE_REQUEST_AADHAAR)

        }
        binding.panBtn.setOnClickListener {
            pickImage(PICK_IMAGE_REQUEST_PAN)

        }
        binding.takeSelfie.setOnClickListener {
            pickImage(PICK_IMAGE_REQUEST_PROFILE)

        }*/
    }
    // here we wrote the codes to pick the image and then set it inside firebase storage for further use
   /* private fun pickImage(requestCode: Int) {
        val items = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == "Take Photo" -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, CAMERA_REQUEST)
                }
                items[item] == "Choose from Gallery" -> {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, GALLERY_REQUEST)
                }
                items[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data

            when (requestCode) {
                PICK_IMAGE_REQUEST_AADHAAR -> uploadImage("aadhaar", selectedImageUri)
                PICK_IMAGE_REQUEST_PAN -> uploadImage("pan", selectedImageUri)
                PICK_IMAGE_REQUEST_PROFILE -> uploadImage("profile", selectedImageUri)
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            selectedImageUri = saveImageToTemporaryFile(imageBitmap)
            // Upload the image to Firebase Storage
            when (requestCode) {
                PICK_IMAGE_REQUEST_AADHAAR -> uploadImage("aadhaar", selectedImageUri)
                PICK_IMAGE_REQUEST_PAN -> uploadImage("pan", selectedImageUri)
                PICK_IMAGE_REQUEST_PROFILE -> uploadImage("profile", selectedImageUri)
            }
        }
    }
    private fun saveImageToTemporaryFile(imageBitmap: Bitmap): Uri {
        val file = File.createTempFile("image", ".jpg", cacheDir)
        val outputStream = FileOutputStream(file)
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return FileProvider.getUriForFile(this, applicationContext.packageName + ".fileprovider", file)
    }
    private fun uploadImage(folderName: String, imageUri: Uri?) {
        if (imageUri != null) {
            val randomKey = UUID.randomUUID().toString()
            val adhrNumber = binding.editAdhaar.text.toString()
            val imageRef = storageReference.child(adhrNumber).child("$folderName/$randomKey.jpg")
            val uploadTask:UploadTask = imageRef.putFile(imageUri)
            // Upload the image to Firebase Storage
            uploadTask.addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully
                    val imageUrl = taskSnapshot.metadata?.reference?.downloadUrl.toString()
                    // Do something with the download URL if needed
                }
                .addOnFailureListener { exception ->
                    // Handle any errors that occur during the upload
                    Log.e("FirebaseStorage", "Upload failed: $exception")
                }
        }
    }


    fun submitData(view: View) {
        val intent: Intent = Intent(this,DashboardActivity::class.java)
        startActivity(intent)
        finish()
       *//* val name = binding.editName.text.toString()
        val aadhaarNumber = binding.editAdhaar.text.toString()
        val panNumber = binding.editPan.text.toString()
        val stateName = binding.editState.text.toString()
        val fullAddress = binding.editAddress.text.toString()
        val phoneNumber = binding.editPhoneNumber.text.toString()
        Toast.makeText(this,selectedImageUri.toString(),Toast.LENGTH_LONG).show()

        if (aadhaarNumber.isEmpty() || panNumber.isEmpty() || stateName.isEmpty() || fullAddress.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter all Fields", Toast.LENGTH_SHORT).show()
        } else if (selectedImageUri != null) {
            val database = FirebaseDatabase.getInstance()
            val reference: DatabaseReference = database.getReference("Users")
            // create a new user
            val newUser = User(name, aadhaarNumber, panNumber, stateName, fullAddress, phoneNumber)
            // Push the new user data to the database
            reference.child(panNumber).setValue(newUser)
            // here we are resetting the user page to null
            binding.editName.setText("")
            binding.editAddress.setText("")
            binding.editAdhaar.setText("")
            binding.editPan.setText("")
            binding.editState.setText("")
            binding.editPhoneNumber.setText("")

        } else {
            Toast.makeText(this, "Please Upload All Images", Toast.LENGTH_SHORT).show()
        }*//*
    }

*/
}