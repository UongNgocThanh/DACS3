
package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3_uongngocthanh_22iteb071.adapter.AdminAdapter
import com.example.dacs3_uongngocthanh_22iteb071.adapter.HotelRecommendedAdminAdapter
import com.example.dacs3_uongngocthanh_22iteb071.viewModel.MainViewModel
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityAdminBinding
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val viewModer = MainViewModel()
    private lateinit var adminAdapter: AdminAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainUpload.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@AdminActivity, UploadAdmin  ::class.java)
            startActivity(intent)

        })
        binding.logoutBtn.setOnClickListener{
            startActivity(Intent(this,LoginPage::class.java))
        }
        binding.mainDelete.setOnClickListener {
            // Hiển thị hộp thoại xác nhận
            showConfirmationDialog()
        }
        innitHotelRecommended()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận hủy")
        builder.setMessage("Bạn có thực sự muốn xóa khách sạn này không?")

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
        val position = adminAdapter.getSelectedItemPosition()
        if (position != RecyclerView.NO_POSITION) {
            val itemToDelete = adminAdapter.getItem(position)
            if (itemToDelete != null) {
                val itemId = itemToDelete.name // Giả sử itemId là đặc điểm định danh của mục trong bảng Hotel

                // Xóa mục khỏi bảng Cart trong Realtime Database
                val databaseReference = FirebaseDatabase.getInstance().getReference("Hotel")
                databaseReference.child(itemId).removeValue()
                    .addOnSuccessListener {
                        // Xóa thành công, load lại trang
                        startActivity(Intent(this, AdminActivity::class.java))
                    }
                    .addOnFailureListener { exception ->
                        // Xóa không thành công, xử lý lỗi nếu cần
                        Log.e("CancelAction", "Error deleting item: $exception")
                    }
            }
        }    }

    private fun innitHotelRecommended(){
//        binding.progressBarHotelRecommended.visibility = View.VISIBLE
        viewModer.hotelRecommended.observe(this, Observer {
            binding.recyclerViewAdmin.layoutManager = GridLayoutManager(this@AdminActivity,2)
            binding.recyclerViewAdmin.adapter = HotelRecommendedAdminAdapter(it)
//            binding.progressBarHotelRecommended.visibility = View.GONE
        })
        viewModer.loadHotelRecommended()
    }

}