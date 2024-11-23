



package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dacs3_uongngocthanh_22iteb071.adapter.ImageAdapter
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityDetailBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelCartItem
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var hotel: HotelModel
    private lateinit var databaseReference: DatabaseReference
    private var mainImageUrl: String = ""

//    private lateinit var managmentCart: ManagmentCart

    private var numberOrder = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        managmentCart = ManagmentCart(this)

        getBundle()
        initLists()
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart")

        binding.bookBtn.setOnClickListener {
            addAppointmentToCart(
                binding.nameCartInput.text.toString().trim(),
                binding.phoneCartInput.text.toString().trim(),
                binding.numberroomInput.text.toString().trim(),
                binding.nameTxt.text.toString().trim(),
                binding.priceTxt.text.toString().trim(),
                mainImageUrl
            )
        }

    }


    private fun getBundle() {
        hotel = intent.getParcelableExtra("object")!!
        binding.nameTxt.text = hotel.name
        binding.addressTxt.text = hotel.address
        binding.priceTxt.text =  hotel.price
        binding.bathroomTxt.text = hotel.numberbath.toString()
        binding.bedTxt.text = hotel.numberbed.toString()
        binding.rateTxt.text = "${hotel.rate} Rating"
        binding.descriptionTxt.text = hotel.description
        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(hotel.picUrl1[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(binding.picMain)

    }


    private fun initLists() {
        val imageList = ArrayList<String>()
        for (imageUrl in hotel.picUrl1) {
            imageList.add(imageUrl)
        }

        mainImageUrl = imageList[0] // Lưu URL của ảnh chính vào biến

        Glide.with(this)
            .load(mainImageUrl)
            .into(binding.picMain)

        binding.picList.adapter = ImageAdapter(imageList, binding.picMain)
        binding.picList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun addAppointmentToCart(
        fullName: String,
        phone: String,
        numberRoom: String,
        hotelName: String,
        price: String,
        imageUrl:String
    ) {
        if (fullName.isEmpty() || phone.isEmpty() || numberRoom.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val id = databaseReference.push().key ?: return
        val cartItem = HotelCartItem(id, fullName, phone, numberRoom, hotelName, price,imageUrl)

        databaseReference.child(id).setValue(cartItem).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Appointment requested successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Failed to request appointment", Toast.LENGTH_SHORT).show()
            }
        }

    }
}