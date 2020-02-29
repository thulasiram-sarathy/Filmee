package com.thul.filmee.view.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.thul.filmee.R
import com.thul.filmee.response.MovieApiResponse
import com.thul.filmee.utils.setupGridManager
import com.thul.filmee.view.activity.HomeActivity
import com.thul.filmee.view.activity.detail.MovieDetailActivity
import com.thul.filmee.view.adapter.HomeController
import com.thul.filmee.view.adapter.MovieClickListener
import com.thul.filmee.viewmodel.home.HomeSearchViewModel
import com.thul.filmee.viewmodel.home.HomeViewModel
import com.thul.filmee.viewmodel.home.SearchViewModelFactory
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.view.*


class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchViewModel: HomeSearchViewModel
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
        val moviesListController = HomeController(object : MovieClickListener {
            override fun onMovieItemClicked(movie: MovieApiResponse) {
//                val action =  HomeFragmentDirections.popularToDetails(movie)
//                root.findNavController().navigate(action)

                activity?.let{
                    val intent = Intent (it, MovieDetailActivity::class.java).apply {
                        putExtra("movie", movie)
                    }
                    it.startActivity(intent)


                }
            }

        })

        root.popularRecyclerview.setupGridManager(moviesListController)

        homeViewModel.popularMoviesLiveData.observe(this, Observer {
            moviesListController.submitList(it)
        })


        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar  = (activity as HomeActivity?)?.supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(false)
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_layout, menu)

        val item = menu?.findItem(R.id.search)
        searchView = item?.getActionView() as SearchView
        searchView.setQueryHint("Search Movie")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                getViewModel(query)

                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }*/

   fun getViewModel(query: String){
       searchViewModel = ViewModelProviders.of(this,SearchViewModelFactory(query)).get(HomeSearchViewModel::class.java)
       val searchListController = HomeController(object : MovieClickListener {
           override fun onMovieItemClicked(movie: MovieApiResponse) {
               val action =  HomeFragmentDirections.popularToDetails(movie)
               findNavController().navigate(action)
           }

       })

       searchViewModel.searchMoviesLiveData.observe(this, Observer {
           searchListController.submitList(it)
       })
       popularRecyclerview.setupGridManager(searchListController)
   }
}