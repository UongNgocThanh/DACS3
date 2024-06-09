package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.dacs3_uongngocthanh_22iteb071.adapter.HotelRecommendedAdapter
import com.example.dacs3_uongngocthanh_22iteb071.adapter.SliderAdapter
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityMainBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.SliderModel
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModer = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        innitBanner()
        innitHotelRecommended()
    }

    private fun innitBanner(){
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModer.banner.observe(this, Observer { item->
            banners(item)
                binding.progressBarBanner.visibility = View.GONE

        })
        viewModer.loadBanners()
    }

    private fun banners(image:List<SliderModel>){
        binding.viewPageSlider.adapter = SliderAdapter(image,binding.viewPageSlider)
        binding.viewPageSlider.clipToPadding=false
        binding.viewPageSlider.clipChildren=false
        binding.viewPageSlider.offscreenPageLimit=3
        binding.viewPageSlider.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewPageSlider.setPageTransformer(compositePageTransformer)
        if (image.size>1){
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPageSlider)
        }
    }

    private fun innitHotelRecommended(){
        binding.progressBarHotelRecommended.visibility = View.VISIBLE
        viewModer.hotelRecommended.observe(this, Observer {
            binding.recommendedView.layoutManager = GridLayoutManager(this@MainActivity,2)
            binding.recommendedView.adapter = HotelRecommendedAdapter(it)
            binding.progressBarHotelRecommended.visibility = View.GONE
        })
        viewModer.loadHotelRecommended()
    }

}