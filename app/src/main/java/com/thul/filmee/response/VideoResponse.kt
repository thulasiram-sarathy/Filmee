package com.thul.filmee.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class VideoResponse(
    @SerializedName("results")val results: List<Video>): Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Video) as ArrayList<Video>
    ) {
    }

    data class Video(
    @SerializedName("id") val id:String?,
    @SerializedName("key") val key:String?,
    @SerializedName("name") val name:String?,
    @SerializedName("type") val type:String?

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }

}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoResponse> {
        override fun createFromParcel(parcel: Parcel): VideoResponse {
            return VideoResponse(parcel)
        }

        override fun newArray(size: Int): Array<VideoResponse?> {
            return arrayOfNulls(size)
        }
    }

}