package com.thul.filmee.view.adapter

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.thul.filmee.response.CreditResponse
import com.thul.filmee.view.epoxyui.CrewViewModel_
import com.thul.filmee.view.epoxyui.LoadingViewModel_

class CrewController() :PagedListEpoxyController<CreditResponse.Crew>(){
    val endReached = false
    override fun buildItemModel(currentPosition: Int, item: CreditResponse.Crew?): EpoxyModel<*> {
        return if (item ==null) {
            LoadingViewModel_()
                .id(-currentPosition)
        } else {

            CrewViewModel_()
                .id(currentPosition)
                .image(item.profile_path)
                .name(item.name)
                .info(item.job)


        }

    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        LoadingViewModel_()
//            .id("loading")
//            .addIf(!endReached && models.isNotEmpty(),this)

    }


}
