package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dacs3_uongngocthanh_22iteb071.adapter.DestinationAdapter
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityDestinationBinding
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.MainViewModel

class DestinationActivity : AppCompatActivity(){

    private var viewModer = MainViewModel()
    private lateinit var binding: ActivityDestinationBinding
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDestinationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.profileRedirect.setOnClickListener{
        startActivity(Intent(this,LoginPage::class.java))
    }

    binding.listBtn.setOnClickListener {
        startActivity(Intent(this@DestinationActivity, CartActivity::class.java))
    }
    binding.searchRedirect.setOnClickListener {
        startActivity(Intent(this@DestinationActivity, SearchActivity::class.java))}
        viewModer = ViewModelProvider(this).get(MainViewModel::class.java)

    binding.backedirect.setOnClickListener{
        finish()
    }
        // Nhận thông tin khu vực từ Intent
        val destination = intent.getStringExtra("destination")

        // Khởi tạo RecyclerView và Adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this@DestinationActivity, 2)

        binding.titleDesTxt.text = "Đây là danh sách khách sạn tại $destination"

        // Quan sát dữ liệu và cập nhật giao diện
        viewModer.destination.observe(this, Observer { hotels ->
            if (hotels.isNullOrEmpty()) {
                binding.titleDesTxt.visibility = View.GONE
                binding.textView10.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.titleDesTxt.visibility = View.VISIBLE

                binding.textView10.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.adapter = DestinationAdapter(hotels)
            }
        })


        // Tải danh sách khách sạn tương ứng với khu vực
        if (destination != null) {
            viewModer.loadHotelsByDestination(destination)
        }
    }

//    initDestination()


}

//    private fun initDestination(){
////        binding.progressBarHotelRecommended.visibility = View.VISIBLE
//        viewModer.destination.observe(this, Observer {
//            binding.recyclerView.layoutManager = GridLayoutManager(this,2)
//            binding.recyclerView.adapter = DestinationAdapter(it)
////            binding.progressBarHotelRecommended.visibility = View.GONE
//        })
//        viewModer.loadHotelNguHanhSon()
//
//    }
