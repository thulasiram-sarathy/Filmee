package com.thul.filmee.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    @SerializedName("poster_path") val poster_path:String,
    @SerializedName("release_date") val releaseDate:String,
    @SerializedName("overview") val overview:String,
    @SerializedName("id") val id:Long,
    @SerializedName("title") val title:String,
    @SerializedName("popularity") val popularity:Double,
    @SerializedName("vote_average") val voteAverage:Double,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_count") val voteCount:Int,
    @SerializedName("genres") val genres:List<ID> = listOf()

):Parcelable


data class MovieListingData(@SerializedName("results")val results: List<Movie>,
                            @SerializedName("page") val page: Int,
                            @SerializedName("total_pages")val total_pages: Int)

/*data class ReviewsListing(@SerializedName("results")val results: List<Review>,
                          @SerializedName("page") val page: Int,
                          @SerializedName("total_pages")val total_pages: Int)*/



/**
 * IDS used to identify movie resource
 */
@Parcelize
data class ID (
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String


):Parcelable