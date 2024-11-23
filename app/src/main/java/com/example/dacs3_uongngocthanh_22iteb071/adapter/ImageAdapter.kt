package com.example.dacs3_uongngocthanh_22iteb071.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs3_uongngocthanh_22iteb071.R
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ViewholderImagedetailBinding

class ImageAdapter(val hotels:MutableList<String>, var picMain:ImageView):
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
        private var selectedPosition = -1
        private var lastSelectedPosition =-1
    private lateinit var context: Context

   inner class ViewHolder(val binding: ViewholderImagedetailBinding):
       RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ViewHolder {
        context = parent.context
        val binding=ViewholderImagedetailBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(hotels[position])
            .into(holder.binding.picList)

        holder.binding.root.setOnClickListener{
            lastSelectedPosition=selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)

            Glide.with(holder.itemView.context)
                .load(hotels[position])
                .into(picMain)
        }

        if (selectedPosition==position){
            holder.binding.imageLayout.setBackgroundColor(R.drawable.grey_bg_selected)
        }else
        {
            holder.binding.imageLayout.setBackgroundColor(R.drawable.grey_bg)
        }

    }


    override fun getItemCount(): Int =hotels.size

}