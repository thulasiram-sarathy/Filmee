package com.thul.filmee.view.activity.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rxjava2.android.samples.utils.getQueryTextChangeObservable
import com.thul.filmee.R
import com.thul.filmee.response.MovieApiResponse
import com.thul.filmee.response.SearchResponse
import com.thul.filmee.utils.setupGridManager
import com.thul.filmee.view.activity.detail.MovieDetailActivity
import com.thul.filmee.view.adapter.HomeController
import com.thul.filmee.view.adapter.MovieClickListener
import com.thul.filmee.view.adapter.SearchClickListener
import com.thul.filmee.view.adapter.SearchController
import com.thul.filmee.viewmodel.home.HomeSearchViewModel
import com.thul.filmee.viewmodel.home.NewSearchViewModelFactory
import com.thul.filmee.viewmodel.home.SearchViewModel
import com.thul.filmee.viewmodel.home.SearchViewModelFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    private lateinit var searchViewModel: HomeSearchViewModel
    private lateinit var searchViewModelNew: SearchViewModel
    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
//       search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchMovie.setImeOptions(EditorInfo.IME_ACTION_SEARCH)
        searchMovie.setIconifiedByDefault(false)
//        search.setOnQueryTextListener(this)

        val searchEditText: EditText =
            searchMovie.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(resources.getColor(android.R.color.white))
        searchEditText.setHintTextColor(resources.getColor(android.R.color.white))
        val myCustomFont: Typeface? =
            ResourcesCompat.getFont(applicationContext, R.font.noto_sans_bold)
        searchEditText.setTypeface(myCustomFont)

        searchView = findViewById(R.id.searchMovie)
                    searchViewModelNew = ViewModelProviders.of(this, NewSearchViewModelFactory()).get(
                        SearchViewModel::class.java)
         setUpSearchObservable()

        searchViewModelNew.apiTweetData().observe(this , Observer {
            searchViewModelNew.fetchMovie()
        })


        val searchListController = SearchController(object : SearchClickListener {
            override fun onSearchMovieItemClicked(movie: SearchResponse.MovieApiResponse) {
                val intent = Intent (applicationContext, SearchDetailActivity::class.java).apply {
                    putExtra("movie", movie)
                }
                startActivity(intent)
            }

        })

        searchRecyclerview.adapter =searchListController.adapter
        searchRecyclerview.setItemSpacingDp(4)

        searchViewModelNew.apiMovieData().observe(this , Observer {
            searchListController.setData(it)
        })

        searchRecyclerview.setupGridManager(searchListController)

    }

    override fun onQueryTextSubmit(query: String): Boolean {
//        getViewModel(query)
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {

        return false
    }


    fun getViewModel(query: String){

        searchViewModel = ViewModelProviders.of(this, SearchViewModelFactory(query)).get(
            HomeSearchViewModel::class.java)

        val searchListController = HomeController(object : MovieClickListener {
            override fun onMovieItemClicked(movie: MovieApiResponse) {
//                val action =  HomeFragmentDirections.popularToDetails(movie)
//                findNavController().navigate(action)
            }

        })

        searchViewModel.searchMoviesLiveData.observe(this, Observer {
            searchListController.submitList(it)

        })
        searchRecyclerview.setupGridManager(searchListController)
    }

         fun setUpSearchObservable() {
             searchView.getQueryTextChangeObservable()
                .debounce(300, TimeUnit.MILLISECONDS)
                /*.filter { text ->
                    if (text.isEmpty()) {
                        runOnUiThread {
//                            textViewResult.text = ""
                        }
                        return@filter false
                    } else {
                        return@filter true
                    }
                }*/
                .distinctUntilChanged()
                .switchMap { query -> Observable.just(query)

                        .doOnError {
                            // handle error
                        }
                        .onErrorReturn { "" }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    searchViewModelNew.fetchMoviesList(result)

                }
        }


}