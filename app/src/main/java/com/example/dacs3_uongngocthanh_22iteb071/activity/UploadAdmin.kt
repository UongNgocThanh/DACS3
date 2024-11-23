package com.example.dacs3_uongngocthanh_22iteb071.activity
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityUploadAdminBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UploadAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityUploadAdminBinding
    private lateinit var database: DatabaseReference
    private var currentImageIndex = 0
    private val storageRef = FirebaseStorage.getInstance().reference
    private var picUrl1 = ArrayList<String>() // Khai báo biến picUrl1 ở đây

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri = result.data!!.data
                uri?.let {
                    try {
                        val inputStream = contentResolver.openInputStream(uri)
                        val myBitmap = BitmapFactory.decodeStream(inputStream)
                        val stream = ByteArrayOutputStream()
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val bytes = stream.toByteArray()
                        val encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                        uploadImage(encodedImage)
                        updateImageView(myBitmap)
                        inputStream?.close()
                    } catch (ex: Exception) {
                        Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadImage1.setOnClickListener { openImagePicker(0) }
        binding.uploadImage2.setOnClickListener { openImagePicker(1) }
        binding.uploadImage3.setOnClickListener { openImagePicker(2) }


        binding.saveButton.setOnClickListener { saveData() }
    }

    private fun openImagePicker(imageIndex: Int) {
        currentImageIndex = imageIndex
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        activityResultLauncher.launch(intent)
    }

    private fun updateImageView(bitmap: Bitmap) {
        when (currentImageIndex) {
            0 -> binding.uploadImage1.setImageBitmap(bitmap)
            1 -> binding.uploadImage2.setImageBitmap(bitmap)
            2 -> binding.uploadImage3.setImageBitmap(bitmap)
        }
    }

    private fun uploadImage(encodedImage: String) {
        val fileName = "image_${System.currentTimeMillis()}.png"
        val imageRef = storageRef.child("images/$fileName")

        val decodedImage = Base64.decode(encodedImage, Base64.DEFAULT)
        val uploadTask = imageRef.putBytes(decodedImage)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // Thêm URL hình ảnh vào danh sách và sau đó lưu dữ liệu
                picUrl1.add(imageUrl)
            }.addOnFailureListener {
                Toast.makeText(this@UploadAdmin, "Failed to get image URL: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this@UploadAdmin, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveData() {
        val name = binding.nameTxt.text.toString()
        val price = binding.priceTxt.text.toString()
        val rating = binding.ratingTxt.text.toString()
        val address = binding.addressTxt.text.toString()
        val description = binding.descriptionTxt.text.toString()
        val numberbed = binding.numberbedTxt.text.toString()
        val numberbath = binding.numberBathTxt.text.toString()
        val destination = binding.destinationTxt.text.toString()

        database = FirebaseDatabase.getInstance().getReference("Hotel")

        database.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var maxId = 0
                if (dataSnapshot.exists()) {
                    val lastItem = dataSnapshot.children.first()
                    maxId = lastItem.key?.toIntOrNull() ?: 0
                }
                val newId = (maxId + 1).toString()
                val item = HotelModel(name, description, picUrl1, price, numberbed, numberbath, rating, address, destination)

                database.child(newId).setValue(item).addOnSuccessListener {
                    binding.nameTxt.text.clear()
                    binding.priceTxt.text.clear()
                    binding.ratingTxt.text.clear()
                    binding.addressTxt.text.clear()
                    binding.numberbedTxt.text.clear()
                    binding.numberBathTxt.text.clear()
                    picUrl1.clear()
//                    clearImageViews()
                    Toast.makeText(this@UploadAdmin, "Data inserted", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this@UploadAdmin, "Data not inserted", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@UploadAdmin, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun clearImageViews() {
//        binding.uploadImage1.setImageResource(R.drawable.baseline_add_photo_alternate_24)
//        binding.uploadImage2.setImageResource(R.drawable.baseline_add_photo_alternate_24)
//        binding.uploadImage3.setImageResource(R.drawable.baseline_add_photo_alternate_24)
//    }
}
