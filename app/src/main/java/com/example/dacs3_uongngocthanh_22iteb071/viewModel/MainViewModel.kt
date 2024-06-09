package com.example.dacs3_uongngocthanh_22iteb071.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel
import com.example.dacs3_uongngocthanh_22iteb071.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel:ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()

    private val _hotelRecommended = MutableLiveData<MutableList<HotelModel>>()

    val hotelRecommended : LiveData<MutableList<HotelModel>> = _hotelRecommended

    val banner : LiveData<List<SliderModel>> = _banner

    fun loadBanners(){
        val Ref = firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun loadHotelRecommended(){

        val Ref = firebaseDatabase.getReference("Hotel")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<HotelModel>()
                for (childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(HotelModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _hotelRecommended.value=lists
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}