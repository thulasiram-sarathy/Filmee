package com.thul.filmee.view.fragment.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thul.filmee.AppConstants
import com.thul.filmee.R
import com.thul.filmee.datasource.CreditViewModelFactory
import com.thul.filmee.datasource.CrewViewModelFactory
import com.thul.filmee.datasource.SimilarModelFactory
import com.thul.filmee.datasource.VideoModelFactory
import com.thul.filmee.response.MovieApiResponse
import com.thul.filmee.response.VideoResponse
import com.thul.filmee.utils.loadTmdbImage
import com.thul.filmee.view.activity.video.VideoActivity
import com.thul.filmee.view.adapter.*
import com.thul.filmee.view.fragment.review.ReviewFragment
import com.thul.filmee.view.fragment.video.VideoFragment
import com.thul.filmee.viewmodel.detail.CreditViewModel
import com.thul.filmee.viewmodel.detail.CrewViewModel
import com.thul.filmee.viewmodel.detail.SimilarViewModel
import com.thul.filmee.viewmodel.video.VideoViewModel
import kotlinx.android.synthetic.main.content_main.*


class DetailFragment :Fragment(){

    val args:DetailFragmentArgs by navArgs()
    private var movieId:Int = 0
    private var navBar: BottomNavigationView? = null
    private lateinit var castCrewViewModel: CreditViewModel
    private lateinit var crewCrewViewModel: CrewViewModel
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var similarViewModel: SimilarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movieArg

        movie?.let {
            movieId = it.id.toInt()
            movieImageView.loadTmdbImage(it.backdropPath)
            textViewTitle.text = it.title
            textViewOverview.text = it.overview
            releaseDateView.text = it.releaseDate!!.split("-")[0]
            ratingBar.rating = it.voteAverage.div(2).toFloat()



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
            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.child_fragment_container, childFragment).commit()

        }


        buttonReviews.setOnClickListener {
            val args = Bundle()
            args.putInt("movieId", movieId)

            val childFragment: Fragment = ReviewFragment()
            childFragment.setArguments(args)
            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.child_fragment_container, childFragment).commit()
        }
        buttonTrailer.setOnClickListener {
            val args = Bundle()
            args.putInt("movieId", movieId)

            val childFragment: Fragment = VideoFragment()
            childFragment.setArguments(args)
            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.child_fragment_container, childFragment).commit()
        }

        videoViewModel = ViewModelProviders.of(this, VideoModelFactory(movieId))
            .get(VideoViewModel::class.java)
        val videoController = VideoController(object : VideoClickListener {


            override fun onVideoItemClicked(video: VideoResponse.Video) {
                val intent = Intent (context, VideoActivity::class.java)
                intent.putExtra(AppConstants.INTENT_VIDEO_KEY,video.key)
                startActivity(intent)
            }

        })

        videoRecyclerview.adapter =videoController.adapter
        videoRecyclerview.setItemSpacingDp(4)


        val linearLayoutManager = LinearLayoutManager(context)
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
                val action =  DetailFragmentDirections.actionMovieDetailsFragmentToReviewsFragment(video)
                findNavController().navigate(action)
            }

        })

        similarRecyclerview.adapter =similarController.adapter
        similarRecyclerview.setItemSpacingDp(4)


        val linearLayoutManagerSimilar = LinearLayoutManager(context)
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



    /*         navBar = activity!!.findViewById(R.id.nav_view)
        val scroll: ScrollView = activity!!.findViewById(R.id.scroll)
        scroll.getViewTreeObserver()
            .addOnScrollChangedListener(OnScrollChangedListener {

                    val scrollY: Int = scroll.getScrollY()
                    val scrollX: Int = scroll.getScrollX()
                    if(scrollY > 20 && navBar!!.isShown){
                        navBar!!.setVisibility(View.GONE)
                    }else if(scrollY < 20 && !navBar!!.isShown){
                        navBar!!.setVisibility(View.VISIBLE)
                    }



            })*/
/*    override fun onStop() {
        super.onStop()
//        val navBar: BottomNavigationView = activity!!.findViewById(R.id.nav_view)
        navBar!!.setVisibility(View.VISIBLE)
        if(scroll.getViewTreeObserver().isAlive){

        }
    }*/

}