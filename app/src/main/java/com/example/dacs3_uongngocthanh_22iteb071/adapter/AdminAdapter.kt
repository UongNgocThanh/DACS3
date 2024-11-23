package com.example.dacs3_uongngocthanh_22iteb071.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3_uongngocthanh_22iteb071.R
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ViewholderItemBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel

class AdminAdapter(private val context: Context, private val hotelItems: MutableList<HotelModel>) :
    RecyclerView.Adapter<AdminAdapter.CartViewHolder>() {


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
        val binding = ViewholderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(hotelItems[position])
    }

    override fun getItemCount(): Int {
        return hotelItems.size
    }

//    fun addItem(item: HotelCartItem) {
//        hotelItems.add(item)
//        notifyItemInserted(hotelItems.size - 1)
//    }

    fun clear() {
        hotelItems.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): HotelModel? {
        if (position in 0 until hotelItems.size) {
            return hotelItems[position]
        }
        return null
    }
    fun removeItem(position: Int) {
        // Xóa phần tử tại vị trí đã cho trong danh sách
        hotelItems.removeAt(position)
        // Thông báo cho RecyclerView biết rằng có sự thay đổi trong dữ liệu
        notifyItemRemoved(position)
    }

    fun setSelectedItem(position: Int) {

    }

    inner class CartViewHolder(private val binding: ViewholderItemBinding) :
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



        fun bind(item: HotelModel) {
            // Binding dữ liệu vào itemView

            // Kiểm tra xem itemView hiện tại có phải là phần tử được chọn hay không
            if (adapterPosition == selectedItemPosition) {
                // Thiết lập background cho itemView khi được chọn
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_selected)
            } else {
                // Thiết lập background cho itemView khi không được chọn
                binding.root.background = null
            }

        }
//        private fun AdminAdapter.updateItems(newItems: List<HotelCartItem>) {
//            this.hotelItems.clear()
//            this.hotelItems.addAll(newItems)
//            notifyDataSetChanged()
//        }
    }
}
