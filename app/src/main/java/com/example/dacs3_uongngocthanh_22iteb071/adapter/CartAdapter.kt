package com.example.dacs3_uongngocthanh_22iteb071.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs3_uongngocthanh_22iteb071.R
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ViewholderCartItemBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelCartItem

class CartAdapter(private val context: Context, private val cartItems: MutableList<HotelCartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }
    // Listener for item click events
    private var itemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ViewholderCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun addItem(item: HotelCartItem) {
        cartItems.add(item)
        notifyItemInserted(cartItems.size - 1)
    }

    fun clear() {
        cartItems.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): HotelCartItem? {
        if (position in 0 until cartItems.size) {
            return cartItems[position]
        }
        return null
    }
    fun removeItem(position: Int) {
        // Xóa phần tử tại vị trí đã cho trong danh sách
        cartItems.removeAt(position)
        // Thông báo cho RecyclerView biết rằng có sự thay đổi trong dữ liệu
        notifyItemRemoved(position)
    }

    fun setSelectedItem(position: Int) {

    }

    inner class CartViewHolder(private val binding: ViewholderCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set click listener for item view
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Cập nhật vị trí của phần tử được chọn
                    selectedItemPosition = position
                    itemClickListener?.invoke(position)

                    // Cập nhật giao diện người dùng
                    notifyDataSetChanged()
                }
            }
        }



        fun bind(item: HotelCartItem) {
            // Binding dữ liệu vào itemView

            // Kiểm tra xem itemView hiện tại có phải là phần tử được chọn hay không
            if (adapterPosition == selectedItemPosition) {
                // Thiết lập background cho itemView khi được chọn
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_selected)
            } else {
                // Thiết lập background cho itemView khi không được chọn
                binding.root.background = null
            }
            binding.apply {
                hotelname.text = item.hotelName
                usernameCart.text = "Họ Tên: "+item.fullName
                phoneCart.text = "Số điện thoại: "+item.phone
                numberRoomCart.text = "Số phòng đặt: "+item.numberRoom
                //loại bỏ dấu $
                val price = item.price.substring(1).toInt()
                // Tính toán giá tổng
                val totalPrice = "$"+price * item.numberRoom.toInt()
                priceTotal.text = totalPrice.toString()

                Glide.with(context)
                    .load(item.picMainUrl)
                    .into(picCart)
            }
        }
    }
}
