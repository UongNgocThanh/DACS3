package com.example.dacs3_uongngocthanh_22iteb071.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch


class SearchViewModel:ViewModel() {

    private val _products = MutableLiveData<List<HotelModel>>()
    val products: LiveData<List<HotelModel>> get() = _products

    private var allProducts = listOf<HotelModel>()

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            val result = try {
                val snapshot = FirebaseDatabase.getInstance().reference.child("Hotel").get().await()
                snapshot.children.mapNotNull { it.getValue(HotelModel::class.java) }
            } catch (e: Exception) {
                emptyList<HotelModel>()
            }
            allProducts = result
            _products.value = result
        }
    }

    fun searchProducts(query: String) {
        val filteredList = allProducts.filter {
            it.name.contains(query, ignoreCase = true)
//            it.address.contains(query, ignoreCase = true)

        }
        _products.value = filteredList
    }
}