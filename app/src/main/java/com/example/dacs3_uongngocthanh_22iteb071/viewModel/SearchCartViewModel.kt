package com.example.dacs3_uongngocthanh_22iteb071.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dacs3_uongngocthanh_22iteb071.model.HotelCartItem
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch


class SearchCartViewModel:ViewModel() {

    private val _products = MutableLiveData<List<HotelCartItem>>()
    val products: LiveData<List<HotelCartItem>> get() = _products

    private var allProducts = listOf<HotelCartItem>()

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            val result = try {
                val snapshot = FirebaseDatabase.getInstance().reference.child("Cart").get().await()
                snapshot.children.mapNotNull { it.getValue(HotelCartItem::class.java) }
            } catch (e: Exception) {
                emptyList<HotelCartItem>()
            }
            allProducts = result
            _products.value = result
        }
    }

    fun searchProducts(query: String) {
        val filteredList = allProducts.filter {
            it.fullName.contains(query, ignoreCase = true)
            it.phone.contains(query, ignoreCase = true)

        }
        _products.value = filteredList
    }
}