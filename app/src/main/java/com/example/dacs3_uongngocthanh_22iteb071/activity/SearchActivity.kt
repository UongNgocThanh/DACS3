package com.example.dacs3_uongngocthanh_22iteb071.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dacs3_uongngocthanh_22iteb071.adapter.HotelRecommendedAdapter
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivitySearchBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: HotelRecommendedAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeRedirect.setOnClickListener{
            startActivity(Intent(this@SearchActivity, MainActivity::class.java))
        }

        binding.profileRedirect.setOnClickListener{
            startActivity(Intent(this,LoginPage::class.java))
        }
        binding.listBtn.setOnClickListener{
            startActivity(Intent(this@SearchActivity,CartActivity::class.java))
        }

//        bottomNavigation()

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        adapter = HotelRecommendedAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager= GridLayoutManager(this,2)
        binding.recyclerView.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.products.observe(this, {
            products -> adapter.updateItems(products)
        })

        // Bộ lọc cho SearchView
        binding.searchBtn.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchProducts(it) }
                return true
            }
        })
    }

    // Cập nhật danh sách các mục trong Adapter
    private fun HotelRecommendedAdapter.updateItems(newItems: List<HotelModel>) {
        this.hotels.clear()
        this.hotels.addAll(newItems)
        notifyDataSetChanged()
    }

//    private fun bottomNavigation() {
//        binding.cartBtn.setOnClickListener { startActivity(Intent(this,CartActivity::class.java))}
//        binding.homeBtn.setOnClickListener { startActivity(Intent(this, TrangChinh::class.java)) }
//        binding.profileBtn.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
//    }
}
