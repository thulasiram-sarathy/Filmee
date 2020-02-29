package com.thul.filmee.view.fragment.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.thul.filmee.AppConstants
import com.thul.filmee.R
import com.thul.filmee.datasource.VideoModelFactory
import com.thul.filmee.response.VideoResponse
import com.thul.filmee.view.activity.video.VideoActivity

import com.thul.filmee.view.adapter.VideoClickListener
import com.thul.filmee.view.adapter.VideoController
import com.thul.filmee.viewmodel.video.VideoViewModel

import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment(){

    private lateinit var videoViewModel: VideoViewModel
    var i = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        videoViewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_video, container, false)
        i = arguments?.getInt("movieId")!!
        val moviesListController = VideoController(object : VideoClickListener {


            override fun onVideoItemClicked(movie: VideoResponse.Video) {

            }

        })

//        root.popularRecyclerview.setupGridManager(moviesListController)


        /*videoViewModel.videoLiveData.observe(this, Observer {
            moviesListController.submitList(it)
        })*/

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        i = arguments?.getInt("movieId")!!

        videoViewModel = ViewModelProviders.of(this, VideoModelFactory(i))
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


/*        val navBar: BottomNavigationView = activity!!.findViewById(R.id.nav_view)
        videoRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0 && navBar.isShown) {
                    navBar.setVisibility(View.GONE)
                } else if (dx < 0) {
                    navBar.setVisibility(View.VISIBLE)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })*/


    }

}