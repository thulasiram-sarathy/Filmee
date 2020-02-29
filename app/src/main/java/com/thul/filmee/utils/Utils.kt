package com.thul.filmee.utils

import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.thul.filmee.response.*
import org.json.JSONObject

val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

fun parseStringToMoviesData(jsonString:String): MovieListingData {


    val jsonObject = JSONObject(jsonString)
    val movies:List<Movie> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<Movie>>(){}.type)
    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)
    return MovieListingData(movies,currentPage,totalPages)


}

fun parseStringToMoviesDataOne(jsonString:String): ApiResponse {


    val jsonObject = JSONObject(jsonString)
    val movies:List<MovieApiResponse> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<MovieApiResponse>>(){}.type)
    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)
    return ApiResponse(movies,currentPage,totalPages)


}


fun parseSearchMoviesData(jsonString:String): ApiResponse {


    val jsonObject = JSONObject(jsonString)
    val movies:List<MovieApiResponse> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<MovieApiResponse>>(){}.type)
    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)
    return ApiResponse(movies,currentPage,totalPages)


}


fun parseSearchData(searchResponse: SearchResponse): SearchResponse {


    val jsonObject = JSONObject(searchResponse.toString())
    val movies:List<SearchResponse.MovieApiResponse> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<MovieApiResponse>>(){}.type)

    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)
    return SearchResponse(movies,currentPage,totalPages)


}

fun parseCastCrewData(jsonString:String): CreditResponse {


    val jsonObject = JSONObject(jsonString)
    val cast:List<CreditResponse.Cast> = gson.fromJson(jsonObject.getJSONArray("cast").toString(),object :TypeToken<List<CreditResponse.Cast>>(){}.type)
    val crew:List<CreditResponse.Crew> = gson.fromJson(jsonObject.getJSONArray("crew").toString(),object :TypeToken<List<CreditResponse.Crew>>(){}.type)
    val id:Int = gson.fromJson(jsonObject.getInt("id").toString(),object : TypeToken<Int>(){}.type)

    return CreditResponse(cast,crew,id)


}


fun parseStringToReviewsData(jsonString: String): ReviewsResponse {
    val jsonObject = JSONObject(jsonString)
    val reviews:List<ReviewsResponse.Review> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<ReviewsResponse.Review>>(){}.type)
    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)

    return ReviewsResponse(reviews,currentPage,totalPages)

}

fun parseStringToVideo(jsonString: String): VideoResponse {
    val jsonObject = JSONObject(jsonString)
    val reviews:List<VideoResponse.Video> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<VideoResponse.Video>>(){}.type)

    return VideoResponse(reviews)

}

fun RecyclerView.setupGridManager(epoxyController: EpoxyController) {
    val spanCount = 2
    val manager = GridLayoutManager(this.context,spanCount)
    epoxyController.spanCount = spanCount
    manager.spanSizeLookup = epoxyController.spanSizeLookup
    this.layoutManager = manager
    this.adapter = epoxyController.adapter
}


fun parseStringSimilarMovies(jsonString:String): ApiResponse {


    val jsonObject = JSONObject(jsonString)
    val movies:List<MovieApiResponse> = gson.fromJson(jsonObject.getJSONArray("results").toString(),object :TypeToken<List<MovieApiResponse>>(){}.type)
    val totalPages:Int = gson.fromJson(jsonObject.getInt("total_pages").toString(),object : TypeToken<Int>(){}.type)
    val currentPage:Int = gson.fromJson(jsonObject.getInt("page").toString(),object : TypeToken<Int>(){}.type)
    return ApiResponse(movies,currentPage,totalPages)


}

fun ImageView.loadTmdbImage(url:String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/w500$url")
        .into(this)

}

fun getAppendedString(idsList:List<ID>):java.lang.StringBuilder {
    val genreString = StringBuilder()
    val lastIndex = idsList.lastIndex
    idsList.mapIndexed {index,value ->
        if (index !=lastIndex) {
            genreString.append("$value,")
        }
        else genreString.append(value)


    }
    return genreString
}