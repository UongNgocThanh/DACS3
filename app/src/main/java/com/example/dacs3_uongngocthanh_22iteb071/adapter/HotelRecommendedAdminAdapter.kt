package com.example.dacs3_uongngocthanh_22iteb071.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ViewholderItemBinding
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel

class HotelRecommendedAdminAdapter(val hotels:MutableList<HotelModel>):RecyclerView.Adapter<HotelRecommendedAdminAdapter.ViewHolder>() {
    private var context : Context? = null
    class ViewHolder(val binding: ViewholderItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelRecommendedAdminAdapter.ViewHolder {
         context = parent.context
        val binding=ViewholderItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelRecommendedAdminAdapter.ViewHolder, position: Int) {
        holder.binding.titleTxt.text = hotels[position].name
        holder.binding.addressTxt.text=hotels[position].address
        holder.binding.priceTxt.text=hotels[position].price
        holder.binding.rateTxt.text = hotels[position].rate.toString()
        holder.binding.bathTxt.text = hotels[position].numberbath.toString()
        holder.binding.bedTxt.text = hotels[position].numberbed.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(hotels[position].picUrl1[0])
            .apply(requestOptions)
            .into(holder.binding.picList)

//        holder.itemView.setOnClickListener{
//            val intent=Intent(holder.itemView.context,DetailActivity::class.java)
//            intent.putExtra("object",hotels[position])
//            holder.itemView.context.startActivity(intent)
//        }

    }


    override fun getItemCount(): Int =hotels.size

}