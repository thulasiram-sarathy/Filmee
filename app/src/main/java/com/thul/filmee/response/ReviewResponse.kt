package com.thul.filmee.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ReviewsResponse(@SerializedName("results")val results: List<Review>,
                          @SerializedName("page") val page: Int,
                          @SerializedName("total_pages")val total_pages: Int): Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Review) as ArrayList<Review>,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    data class Review(
    @SerializedName("id") val id:String?,
    @SerializedName("author") val author:String?,
    @SerializedName("content") val content:String?

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(author)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }

}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
        parcel.writeInt(page)
        parcel.writeInt(total_pages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewsResponse> {
        override fun createFromParcel(parcel: Parcel): ReviewsResponse {
            return ReviewsResponse(parcel)
        }

        override fun newArray(size: Int): Array<ReviewsResponse?> {
            return arrayOfNulls(size)
        }
    }

}