package com.thul.filmee.view.adapter

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.thul.filmee.response.MovieApiResponse
import com.thul.filmee.view.epoxyui.HomeViewModel_
import com.thul.filmee.view.epoxyui.LoadingViewModel_

class HomeController(private val listener:MovieClickListener) :PagedListEpoxyController<MovieApiResponse>(){
    val endReached = false
    override fun buildItemModel(currentPosition: Int, item: MovieApiResponse?): EpoxyModel<*> {
        return if (item ==null) {
            LoadingViewModel_()
                .id(-currentPosition)
        } else {

            HomeViewModel_()
                .id(currentPosition)
                .image(item.poster_path)
                .moviename(item.title)
                .movieRating(item.voteAverage.div(2).toFloat())
                .movieItemClickListener { _, _, _, _ ->
                    listener.onMovieItemClicked(item)
                }

        }

    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        LoadingViewModel_()
//            .id("loading")
//            .addIf(!endReached && models.isNotEmpty(),this)

    }


}

interface MovieClickListener {
    fun onMovieItemClicked(movie: MovieApiResponse)

}