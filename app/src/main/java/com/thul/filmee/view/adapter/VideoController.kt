package com.thul.filmee.view.adapter

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.thul.filmee.response.VideoResponse
import com.thul.filmee.view.epoxyui.LoadingViewModel_
import com.thul.filmee.view.epoxyui.VideoViewModel_

class VideoController(private val listener:VideoClickListener) :PagedListEpoxyController<VideoResponse.Video>(){
    val endReached = false
    override fun buildItemModel(currentPosition: Int, item: VideoResponse.Video?): EpoxyModel<*> {
        return if (item ==null) {
            LoadingViewModel_()
                .id(-currentPosition)
        } else {

            VideoViewModel_()
                .id(currentPosition)
                .video(item.key)
                .title(item.name)
                .type(item.type)
                .videoClickListener() { _, _, _, _ ->
                    listener.onVideoItemClicked(item)
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

interface VideoClickListener {
    fun onVideoItemClicked(movie: VideoResponse.Video)

}