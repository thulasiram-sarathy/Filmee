package com.thul.filmee.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CreditResponse(@SerializedName("cast")val cast: List<Cast>,
                          @SerializedName("crew")val crew: List<Crew>,
                          @SerializedName("id") val id: Int): Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Cast) as ArrayList<Cast>,
        parcel.createTypedArrayList(Crew) as ArrayList<Crew>,
        parcel.readInt()
    ) {
    }

    data class Cast(
    @SerializedName("cast_id") val cast_id:String?,
    @SerializedName("character") val character:String?,
    @SerializedName("name") val name:String?,
    @SerializedName("profile_path") val profile_path:String?

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cast_id)
        parcel.writeString(character)
        parcel.writeString(name)
        parcel.writeString(profile_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cast> {
        override fun createFromParcel(parcel: Parcel): Cast {
            return Cast(parcel)
        }

        override fun newArray(size: Int): Array<Cast?> {
            return arrayOfNulls(size)
        }
    }

}


    data class Crew(
        @SerializedName("credit_id") val credit_id:String?,
        @SerializedName("job") val job:String?,
        @SerializedName("name") val name:String?,
        @SerializedName("profile_path") val profile_path:String?

    ):Parcelable{
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(credit_id)
            parcel.writeString(job)
            parcel.writeString(name)
            parcel.writeString(profile_path)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Crew> {
            override fun createFromParcel(parcel: Parcel): Crew {
                return Crew(parcel)
            }

            override fun newArray(size: Int): Array<Crew?> {
                return arrayOfNulls(size)
            }
        }

    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(cast)
        parcel.writeTypedList(crew)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreditResponse> {
        override fun createFromParcel(parcel: Parcel): CreditResponse {
            return CreditResponse(parcel)
        }

        override fun newArray(size: Int): Array<CreditResponse?> {
            return arrayOfNulls(size)
        }
    }

}