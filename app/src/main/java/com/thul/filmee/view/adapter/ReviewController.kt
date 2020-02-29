package com.thul.filmee.view.adapter

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.thul.filmee.response.ReviewsResponse
import com.thul.filmee.view.epoxyui.LoadingViewModel_
import com.thul.filmee.view.epoxyui.ReviewViewModel_

class ReviewController :PagedListEpoxyController<ReviewsResponse.Review>() {
    val endReached = false
    override fun buildItemModel(currentPosition: Int, item: ReviewsResponse.Review?): EpoxyModel<*> {

        return if(item == null) {
            LoadingViewModel_()
                .id(-currentPosition)

        }  else {
            ReviewViewModel_()
                .id(currentPosition)
                .content(item.content.toString())
                .author(item.author.toString())
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        LoadingViewModel_()
//            .id("loading")
//            .addIf(!endReached && models.isNotEmpty(),this)

    }
}