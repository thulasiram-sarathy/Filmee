package com.thul.filmee.view.fragment.review


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.thul.filmee.R
import com.thul.filmee.datasource.ReviewModelFactory
import com.thul.filmee.view.adapter.ReviewController
import com.thul.filmee.viewmodel.review.ReviewViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*


/**
 * A simple [Fragment] subclass.
 */
class ReviewFragment : Fragment() {

    var i = 0
    val args:ReviewFragmentArgs by navArgs()
    private lateinit var reviewsViewModel: ReviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        i = arguments!!.getInt("movieId")
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        i = arguments?.getInt("movieId")!!
        reviewsViewModel = ViewModelProviders.of(this, ReviewModelFactory(i))
            .get(ReviewViewModel::class.java)
        val reviewsController = ReviewController()
        reviewRecyclerview.adapter =reviewsController.adapter
        reviewRecyclerview.setItemSpacingDp(4)
        reviewsViewModel.reviewsLiveData.observe(this, Observer {
            reviewsController.submitList(it)
        })

     /*   val navBar: BottomNavigationView = activity!!.findViewById(R.id.nav_view)
        reviewRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && navBar.isShown) {
                    navBar.setVisibility(View.GONE)
                } else if (dy < 0) {
                    navBar.setVisibility(View.VISIBLE)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })*/

    }


}
