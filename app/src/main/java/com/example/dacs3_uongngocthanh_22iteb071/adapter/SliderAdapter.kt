package com.example.dacs3_uongngocthanh_22iteb071.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dacs3_uongngocthanh_22iteb071.model.SliderModel
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.dacs3_uongngocthanh_22iteb071.R

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPage2:ViewPager2)
    : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private lateinit var context : Context
    private val runnable = Runnable{
        sliderItems = sliderItems
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item_container,parent,false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position],context)
        if (position==sliderItems.lastIndex-1){
            viewPage2.post(runnable)

        }
    }

    override fun getItemCount(): Int = sliderItems.size


    class SliderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private val imageView:ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(sliderItems: SliderModel, context: Context){
            val requestOptions = RequestOptions().transform(CenterInside(),RoundedCorners(20))

            Glide.with(context)
                .load(sliderItems.url)
                .apply(requestOptions)
                .into(imageView)
        }

    }
}