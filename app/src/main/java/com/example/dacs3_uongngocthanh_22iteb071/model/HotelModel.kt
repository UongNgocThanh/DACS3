package com.example.dacs3_uongngocthanh_22iteb071.model

import android.os.Parcel
import android.os.Parcelable

data class HotelModel(
    var name:String="",
    var description:String="",
    var picUrl1:ArrayList<String> = ArrayList(),
    var price:String="",
    var numberbed:String="",
    var numberbath:String = "",
    var rate:String="",
    var address:String="",
    var destination:String=""

    ):Parcelable{
        constructor(parcel: Parcel):this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.createStringArrayList() as ArrayList<String>,
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()

        )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
        dest.writeStringList(picUrl1)
        dest.writeString(price)
        dest.writeString(numberbed)
        dest.writeString(numberbath)
        dest.writeString(rate)
        dest.writeString(address)
        dest.writeString(destination)
    }


    companion object CREATOR : Parcelable.Creator<HotelModel> {
        override fun createFromParcel(parcel: Parcel): HotelModel {
            return HotelModel(parcel)
        }

        override fun newArray(size: Int): Array<HotelModel?> {
            return arrayOfNulls(size)
        }
    }
}

