package com.thul.filmee.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("release_date") val releaseDate:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("id") val id:Long,
    @SerializedName("title") val title:String?,
    @SerializedName("popularity") val popularity:Double,
    @SerializedName("vote_average") val voteAverage:Double,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_count") val voteCount:Int/*,
    @SerializedName("genres") val genres:List<ID> = listOf()*/) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt()/*,
            parcel.createTypedArrayList()*/) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poster_path)
        parcel.writeString(releaseDate)
        parcel.writeString(overview)
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeDouble(popularity)
        parcel.writeDouble(voteAverage)
        parcel.writeString(backdropPath)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieApiResponse> {
        override fun createFromParcel(parcel: Parcel): MovieApiResponse {
            return MovieApiResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieApiResponse?> {
            return arrayOfNulls(size)
        }
    }
}