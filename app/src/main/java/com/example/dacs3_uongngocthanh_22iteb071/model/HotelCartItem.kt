package com.example.dacs3_uongngocthanh_22iteb071.model

data class HotelCartItem(
    var id: String = "",
    var fullName: String = "",
    var phone: String = "",
    var numberRoom: String = "",
    var hotelName: String = "",
    var price: String = "",
    var picMainUrl: String = ""
) {
    // Constructor without arguments
    constructor() : this("", "", "", "", "", "", "")
}
