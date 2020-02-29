package com.thul.filmee.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results")val results: List<MovieApiResponse>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages")val total_pages: Int) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(MovieApiResponse) as ArrayList<MovieApiResponse>,
        parcel.readInt(),
        parcel.readInt()) {
    }




    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
        parcel.writeInt(page)
        parcel.writeInt(total_pages)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiResponse> {
        override fun createFromParcel(parcel: Parcel): ApiResponse {
            return ApiResponse(parcel)
        }

        override fun newArray(size: Int): Array<ApiResponse?> {
            return arrayOfNulls(size)
        }
    }
    }
