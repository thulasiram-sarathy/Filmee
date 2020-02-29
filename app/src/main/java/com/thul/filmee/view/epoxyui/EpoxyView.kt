package com.thul.filmee.view.epoxyui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.an.customfontview.CustomTextView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.thul.filmee.AppConstants.Companion.YT_KEY
import com.thul.filmee.R
import com.thul.filmee.utils.circleimageview.CircleImageView
import com.thul.filmee.utils.loadTmdbImage


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,fullSpan = false)
class HomeView @JvmOverloads constructor(context: Context,
                                          attributes: AttributeSet?=null,
                                          defStyle:Int = 0)
    :CardView(context,attributes,defStyle)
{


    private val posterImage:ImageView
    private val movieName:TextView
    private val movieRating:RatingBar
    private val movieCardView:CardView

    init {
        View.inflate(context, R.layout.movie_item,this)
        posterImage = findViewById(R.id.posterImageView)
        movieName = findViewById(R.id.movieName)
        movieRating = findViewById(R.id.movieRating)
        movieCardView = findViewById(R.id.movieCardLayout)
    }

    @ModelProp
    fun setImage(url:String?)
    {
        posterImage.loadTmdbImage(url)

    }

    @ModelProp
    fun setMoviename(movie:String?)
    {
        movieName.text = movie

    }
    @ModelProp
    fun setMovieRating(rating:Float)
    {
        movieRating.rating = rating

    }

    @CallbackProp
    fun setMovieItemClickListener(listener: OnClickListener?) {
        movieCardView.setOnClickListener(listener)
    }


}




@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,fullSpan = false)
class LoadingView @JvmOverloads constructor(context: Context,
                                            attributes: AttributeSet?=null,
                                            defStyle:Int = 0)
    :LinearLayout(context,attributes,defStyle)
{
    init {
        View.inflate(context,R.layout.loading_item,this)
    }
}


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,fullSpan = false)
class ReviewView @JvmOverloads constructor(context: Context,
                                           attributes: AttributeSet?=null,
                                           defStyle:Int = 0):CardView(context,attributes,defStyle) {
    private val authorTextView: CustomTextView
    private val contentTextView: ExpandableTextView

    init {
        View.inflate(context, R.layout.review_layout, this)
        authorTextView = findViewById(R.id.authorTextView)
        contentTextView = findViewById(R.id.contentTextView)
    }


    @TextProp
    fun setAuthor(author: CharSequence){
        authorTextView.text = author
    }

    @TextProp
    fun setContent(content:CharSequence) {
        contentTextView.text = content
    }


}


@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_MATCH_HEIGHT,fullSpan = false)
class VideoView @JvmOverloads constructor(context: Context,
                                           attributes: AttributeSet?=null,
                                           defStyle:Int = 0):CardView(context,attributes,defStyle) {
    private val youtube_thumbnail : YouTubeThumbnailView
    private val share_btn: AppCompatImageView
    private val btn_play :AppCompatImageView
    private val vid_frame : ConstraintLayout

    init {
        View.inflate(context, R.layout.movie_item_video, this)
        youtube_thumbnail  = findViewById(R.id.youtube_thumbnail)
        share_btn  = findViewById(R.id.share_btn)
        btn_play  = findViewById(R.id.btn_play)
        vid_frame  = findViewById(R.id.vid_frame)
        /*addView(LinearLayout(context).also {
            val params = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            params.gravity = Gravity.CENTER_HORIZONTAL
            it.layoutParams = params
            it.orientation = LinearLayout.HORIZONTAL
        })*/
    }


    @ModelProp
    fun setVideo(videokey: String?){
        youtube_thumbnail.initialize(YT_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(
                youTubeThumbnailView: YouTubeThumbnailView,
                youTubeThumbnailLoader: YouTubeThumbnailLoader
            ) {

                youTubeThumbnailLoader.setVideo(videokey)
                youTubeThumbnailView.setImageBitmap(null)
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(object :
                    OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(
                        youTubeThumbnailView: YouTubeThumbnailView,
                        s: String
                    ) {
                        youTubeThumbnailView.visibility = View.VISIBLE
                        vid_frame.visibility = View.VISIBLE
                        btn_play.setImageResource(R.drawable.ic_play)
                        youTubeThumbnailLoader.release()
                    }

                    override fun onThumbnailError(
                        youTubeThumbnailView: YouTubeThumbnailView,
                        errorReason: YouTubeThumbnailLoader.ErrorReason
                    ) {
                    }
                })
            }

            override fun onInitializationFailure(
                youTubeThumbnailView: YouTubeThumbnailView,
                youTubeInitializationResult: YouTubeInitializationResult
            ) { //write something for failure
            }
        })
    }

    @ModelProp
    fun setTitle(name:String?) {

    }

    @ModelProp
    fun setType(type:String?) {

    }


    @CallbackProp
    fun setVideoClickListener(listener: OnClickListener?) {
        vid_frame.setOnClickListener(listener)
    }

}

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_MATCH_HEIGHT,fullSpan = false)
class CastView @JvmOverloads constructor(context: Context,
                                           attributes: AttributeSet?=null,
                                           defStyle:Int = 0):CardView(context,attributes,defStyle) {

    private val profile_image : CircleImageView
    private val  txt_name : AppCompatTextView
    private val  txt_info: AppCompatTextView

    init {
        View.inflate(context, R.layout.list_cast, this)
        profile_image = findViewById(R.id.profile_image)
        txt_info = findViewById(R.id.txt_info)
        txt_name = findViewById(R.id.txt_name)
    }

    @ModelProp
    fun setImage(url:String?)
    {
        profile_image.loadTmdbImage(url)

    }

    @ModelProp
    fun setName(name: String?){
        txt_name.text = name
    }

    @ModelProp
    fun setInfo(info:String?) {
        txt_info.text = info
    }


}

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_MATCH_HEIGHT,fullSpan = false)
class CrewView @JvmOverloads constructor(context: Context,
                                         attributes: AttributeSet?=null,
                                         defStyle:Int = 0):CardView(context,attributes,defStyle) {

    private val profile_image : CircleImageView
    private val  txt_name : AppCompatTextView
    private val  txt_info: AppCompatTextView

    init {
        View.inflate(context, R.layout.list_cast, this)
        profile_image = findViewById(R.id.profile_image)
        txt_info = findViewById(R.id.txt_info)
        txt_name = findViewById(R.id.txt_name)
    }

    @ModelProp
    fun setImage(url:String?)
    {
        profile_image.loadTmdbImage(url)

    }

    @ModelProp
    fun setName(name: String?){
        txt_name.text = name
    }

    @ModelProp
    fun setInfo(info:String?) {
        txt_info.text = info
    }


}

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_MATCH_HEIGHT,fullSpan = false)
class SimilarView @JvmOverloads constructor(context: Context,
                                         attributes: AttributeSet?=null,
                                         defStyle:Int = 0)
    :CardView(context,attributes,defStyle)
{


    private val posterImage:ImageView
    private val movieName:TextView
    private val movieRating:RatingBar
    private val movieCardView:CardView

    init {
        View.inflate(context, R.layout.similar_item,this)
        posterImage = findViewById(R.id.posterImageView)
        movieName = findViewById(R.id.movieName)
        movieRating = findViewById(R.id.movieRating)
        movieCardView = findViewById(R.id.movieCardLayout)
    }

    @ModelProp
    fun setImage(url:String?)
    {
        posterImage.loadTmdbImage(url)

    }

    @ModelProp
    fun setMoviename(movie:String?)
    {
        movieName.text = movie

    }
    @ModelProp
    fun setMovieRating(rating:Float)
    {
        movieRating.rating = rating

    }

    @CallbackProp
    fun setMovieItemClickListener(listener: OnClickListener?) {
        movieCardView.setOnClickListener(listener)
    }


}