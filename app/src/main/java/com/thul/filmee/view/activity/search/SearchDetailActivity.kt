package com.thul.filmee.view.activity.search

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.thul.filmee.AppConstants
import com.thul.filmee.R
import com.thul.filmee.datasource.CreditViewModelFactory
import com.thul.filmee.datasource.CrewViewModelFactory
import com.thul.filmee.datasource.SimilarModelFactory
import com.thul.filmee.datasource.VideoModelFactory
import com.thul.filmee.response.MovieApiResponse
import com.thul.filmee.response.SearchResponse
import com.thul.filmee.response.VideoResponse
import com.thul.filmee.utils.loadTmdbImage
import com.thul.filmee.view.activity.video.VideoActivity
import com.thul.filmee.view.adapter.*
import com.thul.filmee.view.fragment.detail.DetailFragmentDirections
import com.thul.filmee.view.fragment.review.ReviewFragment
import com.thul.filmee.viewmodel.detail.CreditViewModel
import com.thul.filmee.viewmodel.detail.CrewViewModel
import com.thul.filmee.viewmodel.detail.SimilarViewModel
import com.thul.filmee.viewmodel.video.VideoViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*


class SearchDetailActivity : AppCompatActivity() {

    private var movieId:Int = 0

    private lateinit var castCrewViewModel: CreditViewModel
    private lateinit var crewCrewViewModel: CrewViewModel
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var similarViewModel: SimilarViewModel

    private var toolbar: Toolbar? = null
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var appBarLayout: AppBarLayout? = null
    private var recList: RecyclerView? = null
    private var collapseMenu: Menu? = null
    private var appBarExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra("movie") as? SearchResponse.MovieApiResponse
/*        if(movie is MovieApiResponse){
            Log.v("MovieApiResponse", " "+ "MovieApiResponse")
        }else{
            Log.v("MovieApiResponse", " "+ "search")
        }*/

        toolbar = findViewById<View>(R.id.anim_toolbar) as Toolbar
        collapsingToolbar =
            findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        appBarLayout = findViewById<View>(R.id.appbar) as AppBarLayout

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar!!.title = " "

        appBarLayout!!.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            appBarExpanded = if (Math.abs(verticalOffset) > 200) {
                false
            } else {
                true
            }
            invalidateOptionsMenu()
        })

        movie?.let {
            movieId = it.id.toInt()
            header.loadTmdbImage(it.backdropPath)
            textViewTitle.text = it.title
            textViewOverview.text = it.overview
            releaseDateView.text = it.releaseDate!!.split("-")[0]
//            ratingBar.rating = it.voteAverage.div(2).toFloat()



            castCrewViewModel = ViewModelProviders.of(this, CreditViewModelFactory(movieId))
                .get(CreditViewModel::class.java)

            crewCrewViewModel = ViewModelProviders.of(this, CrewViewModelFactory(movieId))
                .get(CrewViewModel::class.java)

            similarViewModel = ViewModelProviders.of(this, SimilarModelFactory(movieId))
                .get(SimilarViewModel::class.java)

            val args = Bundle()
            args.putInt("movieId", movieId)

            val childFragment: Fragment = ReviewFragment()
            childFragment.setArguments(args)
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.child_fragment_container, childFragment).commit()

        }

        videoViewModel = ViewModelProviders.of(this, VideoModelFactory(movieId))
            .get(VideoViewModel::class.java)
        val videoController = VideoController(object : VideoClickListener {


            override fun onVideoItemClicked(video: VideoResponse.Video) {
                val intent = Intent (applicationContext, VideoActivity::class.java)
                intent.putExtra(AppConstants.INTENT_VIDEO_KEY,video.key)
                startActivity(intent)
            }

        })

        videoRecyclerview.adapter =videoController.adapter
        videoRecyclerview.setItemSpacingDp(4)


        val linearLayoutManager = LinearLayoutManager(this)
        videoRecyclerview.apply {
            layoutManager = linearLayoutManager
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
            setHasFixedSize(true)
            adapter = videoController.adapter
//            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation ))
        }
        videoViewModel.videoLiveData.observe(this, Observer {
            videoController.submitList(it)
        })


        similarViewModel = ViewModelProviders.of(this, SimilarModelFactory(movieId))
            .get(SimilarViewModel::class.java)
        val similarController = SimilarController(object : SimilarMovieClickListener {


            override fun onSimilarMovieItemClicked(video: MovieApiResponse) {
//                val action =  DetailFragmentDirections.actionMovieDetailsFragmentToReviewsFragment(video)
//                findNavController().navigate(action)
                val intent = Intent (applicationContext, SearchDetailActivity::class.java).apply {
                    putExtra("movie", video)
                }
                startActivity(intent)
            }

        })
        similarRecyclerview.adapter =similarController.adapter
        similarRecyclerview.setItemSpacingDp(4)


        val linearLayoutManagerSimilar = LinearLayoutManager(this)
        similarRecyclerview.apply {
            layoutManager = linearLayoutManagerSimilar
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
            setHasFixedSize(true)
            adapter = similarController.adapter
//            addItemDecoration(DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation ))
        }
        similarViewModel.similarLiveData.observe(this, Observer {
            similarController.submitList(it)
        })

        val castController = CastController()
        castRecyclerview.adapter =castController.adapter
        castRecyclerview.setItemSpacingDp(4)

        val crewController = CrewController()
        crewRecyclerview.adapter =crewController.adapter
        crewRecyclerview.setItemSpacingDp(4)

        castCrewViewModel.castLiveData.observe(this, Observer {
            castController.submitList(it)
        })

        crewCrewViewModel.crewLiveData.observe(this, Observer {
            crewController.submitList(it)
        })

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (collapseMenu != null && (!appBarExpanded || collapseMenu?.size() !== 1)) { //collapsed
            collapseMenu?.add("Add")
                ?.setIcon(R.drawable.ic_bookmark_deselected)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else {
        }
        return super.onPrepareOptionsMenu(collapseMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        collapseMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           /* R.id.home -> finish()*/
            R.id.bookmark -> Toast.makeText(
                this,
                "Bookmark menu clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (item.getTitle() === "Add") {
            Toast.makeText(this, "Add menu clicked!", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
