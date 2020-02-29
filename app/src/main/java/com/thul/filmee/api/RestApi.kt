package com.an.github.rest


import com.thul.filmee.AppConstants.Companion.BASE_URL
import com.thul.filmee.response.SearchResponse
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestApi {

    @GET("movie/{type}")
    fun getMovies(@Path("type") type:String, @Query("api_key") api_key:String, @Query("page") page:Int): Call<ResponseBody>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(@Path("movie_id") movieId:Int, @Query("api_key") api_key:String, @Query("page") page:Int): Call<ResponseBody>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") movieId:Int, @Query("api_key") api_key:String): Call<ResponseBody>

    @GET("movie/{movie_id}/credits")
    fun getCastCrew(@Path("movie_id") movieId:Int, @Query("api_key") api_key:String): Call<ResponseBody>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId:Int, @Query("api_key") api_key:String): Call<ResponseBody>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String?,@Query("api_key") api_key:String, @Query("page") page: Int?): Call<ResponseBody>

    @GET("search/movie")
    fun getMoviesList(@Query("query") query: String?,@Query("api_key") api_key:String, @Query("page") page: Int?
    ): Single<SearchResponse>

    companion object {

        fun create(httpUrl: HttpUrl): RestApi {
            val okHttpClient = OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestApi::class.java)
        }


        fun create(): RestApi = create(HttpUrl.parse(BASE_URL)!!)
    }

}