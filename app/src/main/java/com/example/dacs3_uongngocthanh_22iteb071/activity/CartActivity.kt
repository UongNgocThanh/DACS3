package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3_uongngocthanh_22iteb071.adapter.CartAdapter
import com.example.dacs3_uongngocthanh_22iteb071.adapter.HotelRecommendedAdapter
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityCartBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelCartItem
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.SearchCartViewModel
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.SearchViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var viewModel: SearchCartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelBtn.setOnClickListener {
            // Hiển thị hộp thoại xác nhận
            showConfirmationDialog()
        }

        binding.profileRedirect.setOnClickListener{
            startActivity(Intent(this,LoginPage::class.java))
        }
        binding.listBtn.setOnClickListener{
            startActivity(Intent(this@CartActivity, CartActivity::class.java))
        }
        binding.searchRedirect.setOnClickListener{
            startActivity(Intent(this@CartActivity,SearchActivity::class.java))
        }
        binding.homeRedirect.setOnClickListener{
            startActivity(Intent(this@CartActivity,MainActivity::class.java))
        }
        viewModel = ViewModelProvider(this).get(SearchCartViewModel::class.java)

        cartAdapter = CartAdapter(this,mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager= GridLayoutManager(this,2)
        binding.recyclerView.adapter = cartAdapter

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this, mutableListOf())
        binding.recyclerView.adapter = cartAdapter

//        viewModel.products.observe(this, {
//                products -> cartAdapter.updateItems(products)
//        })

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

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart")

        // Listen for changes in "Cart" node
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data
                cartAdapter.clear()

                // Iterate through each child node and add to list
                for (childSnapshot in snapshot.children) {
                    val cartItem = childSnapshot.getValue(HotelCartItem::class.java)
                    cartItem?.let { cartAdapter.addItem(it) }
                }

                // Notify adapter about data changes
                cartAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })

        // Set click listener for item click events
        cartAdapter.setOnItemClickListener { position ->
            cartAdapter.setSelectedItem(position)
            // Add your logic for item click here
        }



        binding.homeRedirect.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận hủy")
        builder.setMessage("Bạn có thực sự muốn hủy đặt phòng này không?")

        // Nếu người dùng nhấn Yes, thực hiện hành động hủy
        builder.setPositiveButton("Yes sir") { dialog, which ->
            cancelAction()
        }

        // Nếu người dùng nhấn No, đóng hộp thoại
        builder.setNegativeButton("No ") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun cancelAction() {
        val position = cartAdapter.getSelectedItemPosition()
        if (position != RecyclerView.NO_POSITION) {
            val itemToDelete = cartAdapter.getItem(position)
            if (itemToDelete != null) {
                val itemId = itemToDelete.id // Giả sử itemId là đặc điểm định danh của mục trong bảng Cart

                // Xóa mục khỏi bảng Cart trong Realtime Database
                val databaseReference = FirebaseDatabase.getInstance().getReference("Cart")
                databaseReference.child(itemId).removeValue()
                    .addOnSuccessListener {
                        // Xóa thành công, load lại trang
                        startActivity(Intent(this, CartActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        // Xóa không thành công, xử lý lỗi nếu cần
                        Log.e("CancelAction", "Error deleting item: $exception")
                    }
            }
        }
    }

}
