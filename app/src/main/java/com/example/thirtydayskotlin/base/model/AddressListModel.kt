package com.example.thirtydayskotlin.base.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressListModel {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("pinCode")
    @Expose
    var pinCode: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("cityBoundaryBreached")
    @Expose
    var cityBoundaryBreached: Boolean? = null
    @SerializedName("pinCodeBoundaryBreached")
    @Expose
    var pinCodeBoundaryBreached: Boolean? = null
    @SerializedName("addressType")
    @Expose
    var addressType: String? = null
    @SerializedName("addressString")
    @Expose
    var addressString: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null
    @SerializedName("errorMargin")
    @Expose
    var errorMargin: Int? = null

}